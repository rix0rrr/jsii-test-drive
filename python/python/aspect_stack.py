import jsii
from aws_cdk import core
import aws_cdk.aws_sns as sns
import aws_cdk.aws_iam as iam

class AspectStack(core.Stack):
    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        sns.Topic(self, 'Topic', display_name='xxx')

        self.node.apply_aspect(MyAspect())



@jsii.implements(core.IAspect)
class MyAspect:
  def __init__(self):
    self.counter = 0

  def visit(self, construct):
    print('----')
    print(construct)
    print(construct.node.try_find_child('Resource'))

    # Do a silly thing. We KNOW what order we're visiting
    # constructs in, so we know the types of the things we're
    # expecting. No type tests, just accesses!
    self.counter += 1

    if self.counter == 1:
      # Stack
      print(construct.stack_name)
      print(construct.format_arn(region='here', service='there', resource='thing'))
    if self.counter == 2:
      # Topic
      print(construct.topic_arn)
      construct.grant_publish(iam.ServicePrincipal('doesnotexist.amazonaws.com'))
    if self.counter == 3:
      # CfnTopic
      print(construct.cfn_resource_type)
      construct.apply_removal_policy(core.RemovalPolicy.RETAIN)
      construct.add_depends_on(construct) # This will make it undeployable
      assert construct.display_name == 'xxx'
      construct.display_name = 'yyy'
