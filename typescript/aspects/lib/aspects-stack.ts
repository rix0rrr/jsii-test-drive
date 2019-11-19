import iam = require('@aws-cdk/aws-iam');
import sns = require('@aws-cdk/aws-sns');
import cdk = require('@aws-cdk/core');

export class AspectsStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    new sns.Topic(this, 'Topic', { displayName: 'xxx' });

    this.node.applyAspect(new MyAspect());
  }
}

class MyAspect implements cdk.IAspect {
  private counter = 0;

  public visit(construct: cdk.IConstruct): void {
    console.log('----');
    console.log(construct);
    console.log(construct.node.tryFindChild('Resource'));

    this.counter += 1;
    switch (this.counter) {
      case 1:
        console.log((construct as cdk.Stack).stackName);
        console.log((construct as cdk.Stack).formatArn({ region: 'here', service: 'there', resource: 'thing' }));
        break;
      case 2:
        console.log((construct as sns.ITopic).topicArn);
        (construct as sns.ITopic).grantPublish(new iam.ServicePrincipal('doesnotexist.amazonaws.com'));
        break;
      case 3:
        console.log((construct as cdk.CfnResource).cfnResourceType);
        (construct as cdk.CfnResource).applyRemovalPolicy(cdk.RemovalPolicy.RETAIN);
        (construct as cdk.CfnResource).addDependsOn(construct as cdk.CfnResource);
        if ((construct as sns.CfnTopic).displayName !== 'xxx') {
          throw new Error('Topic display name is not xxx');
        }
        (construct as sns.CfnTopic).displayName = 'yyy';
        break;
      case 4:
        throw new Error('Should not have reached this far!');
    }
  }
}
