from aws_cdk import core
import aws_cdk.aws_sns as sns
import aws_cdk.aws_s3 as s3


class PythonStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        sns.Topic(self, 'Topic', display_name='Hallo!')
        bucket = s3.Bucket(self, 'Bucket', website_index_document='index.html')

        # Overrides
        cfn_bucket = bucket.node.default_child
        cfn_bucket.add_property_override('AccelerateConfiguration', {
          'AccelerationStatus': 'Enabled'
        })
        cfn_bucket.website_configuration = s3.CfnBucket.WebsiteConfigurationProperty(
            index_document=cfn_bucket.website_configuration.index_document,
            error_document='error.html')
