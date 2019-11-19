from aws_cdk import core
import aws_cdk.aws_lambda as lamda


is_called = False


class HelloStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        lm = lamda.Function(self, 'Lamna',
            handler='index.handler',
            runtime=lamda.Runtime.DOTNET_CORE_1,
            code=MyCode()
            )

        layer = lamda.LayerVersion(self, 'LV1',
            code=MyCode(),
            compatible_runtimes=[lamda.Runtime.DOTNET_CORE_1])

        assert is_called


class MyCode(lamda.Code):
  def bind(self, construct):
    fakey_resource = core.CfnResource(construct, 'Bogus', type='AWS::Nothing::Bye')
    self.bind_to_resource(fakey_resource) # Inherited method that does nothing
    # return dict(inlineCode="Henk")
    return dict(s3Location=dict(bucketName="Henk", objectKey="Doei.zip"))

  # NOTE: Non-trivial, have to put @property here
  @property
  def is_inline(self):
    global is_called
    is_called = True
    return False
