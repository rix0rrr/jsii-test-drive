import events = require('@aws-cdk/aws-events');
import cdk = require('@aws-cdk/core');

export class CallbackOptionalProvidedStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    new events.Rule(this, 'Rule', {
      targets: [new MyEventTarget()],
      schedule: events.Schedule.cron({ day: '20' }),
    });
  }
}

class MyEventTarget implements events.IRuleTarget {
  public bind(rule: events.IRule, id?: string): events.RuleTargetConfig {
    if (!id) {
      throw new Error("Expected 'id' to be passed, but it was not!");
    }

    return {
      id: 'asdf',
      arn: 'target-arn',
      ecsParameters: { taskDefinitionArn: 'task-definition-arn' },
    };
  }
}
