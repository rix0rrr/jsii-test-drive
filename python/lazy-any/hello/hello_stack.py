import jsii
from aws_cdk import core
import aws_cdk.aws_s3 as s3


class HelloStack(core.Stack):
    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        s3.Bucket(self, 'Bucket',
            bucket_name=core.Token.as_string(core.Lazy.any_value(Difficult('bucket-name'))),
            lifecycle_rules=[
              core.Lazy.any_value(Difficult(s3.LifecycleRule(
                id='asdf'
                )))
              ]
            )


@jsii.implements(core.IAnyProducer)
class Difficult:
  def __init__(self, value):
    self.value = value

  def produce(self, context):
    return self.value

