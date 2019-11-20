import ec2 = require('@aws-cdk/aws-ec2');
import elb = require('@aws-cdk/aws-elasticloadbalancingv2');
import events = require('@aws-cdk/aws-events');
import cdk = require('@aws-cdk/core');

export class ReturnStructFromCallbacksStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    new events.Rule(this, 'Rule', { targets: [new MyEventTarget()], schedule: events.Schedule.cron({ day: '20' }) });

    const lb = new elb.ApplicationLoadBalancer(this, 'LB', { vpc: new ec2.Vpc(this, 'Vpc') });
    const listener = lb.addListener('Listener', { port: 80 });
    listener.addTargets('DefaultTargets', { targets: [new MyLBTarget()], port: 80 });
  }
}

class MyEventTarget implements events.IRuleTarget {
  public bind(rule: events.IRule, id?: string): events.RuleTargetConfig {
    return {
      id: 'asdf',
      arn: 'target-arn',
      ecsParameters: { taskDefinitionArn: 'task-definition-arn' },
    };
  }
}

class MyLBTarget implements elb.IApplicationLoadBalancerTarget {
  public attachToApplicationTargetGroup(targetGroup: elb.IApplicationTargetGroup): elb.LoadBalancerTargetProps {
    return {
      targetType: elb.TargetType.IP,
      targetJson: { port: 1234, id: 'i-1234' }
    };
  }
}
