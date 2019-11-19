from aws_cdk import core
import aws_sns.aws_sns as sns

class EscapeHatchStack(core.Stack):
    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        sns.Topic(self, 'Topic')
