import s3 = require('@aws-cdk/aws-s3');
import sns = require('@aws-cdk/aws-sns');
import cdk = require('@aws-cdk/core');

export class L1AndOverridesStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    new sns.Topic(this, 'Topic', { displayName: 'Hallo!' });
    const bucket = new s3.Bucket(this, 'Bucket', { websiteIndexDocument: 'index.html' });

    const cfnBucket = bucket.node.defaultChild as s3.CfnBucket;
    cfnBucket.addPropertyOverride('AccelerateConfiguration', { AccelerationStatus: 'Enabled' });
    cfnBucket.websiteConfiguration = {
      errorDocument: 'error.html',
      indexDocument: (cfnBucket.websiteConfiguration! as s3.CfnBucket.WebsiteConfigurationProperty).indexDocument,
    };
  }
}
