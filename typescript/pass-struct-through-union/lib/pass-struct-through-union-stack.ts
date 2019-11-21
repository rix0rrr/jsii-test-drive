import sam = require('@aws-cdk/aws-sam');
import cdk = require('@aws-cdk/core');

export class PassStructThroughUnionStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const fun = new sam.CfnFunction(this, 'Fn', {
      codeUri: 'asdf',
      handler: 'index.handler',
      runtime: 'runtime',
      events: {
        'Event': {
          type: 'type1',
          properties: {
            queue: 'queue-arn',
          },
        },
      },
    });

    console.log((fun.events as any)['Event'].properties);
    const queueArn = (fun.events as any)['Event'].properties.queue;
    if (queueArn !== 'queue-arn') {
      throw new Error("Expected fun.events.event.properties.queue to be 'queue-arn', " +
          `but was: '${queueArn}'`);
    }
  }
}
