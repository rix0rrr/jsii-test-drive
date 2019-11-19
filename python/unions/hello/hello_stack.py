from aws_cdk import core
import aws_cdk.aws_sam as sam


class HelloStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        fun = sam.CfnFunction(self, 'Fn',
            code_uri='asdf',
            handler='index.handler',
            runtime='runtime',
            events={
              'Event' : sam.CfnFunction.EventSourceProperty(
                type='type1',
                properties=sam.CfnFunction.SQSEventProperty(queue='queue-arn'))
              })

        print(fun.events['Event'].properties)
        assert fun.events['Event'].properties.queue == 'queue-arn'
