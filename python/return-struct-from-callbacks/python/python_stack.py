import jsii
from aws_cdk import core
import aws_cdk.aws_events as events
import aws_cdk.aws_ec2 as ec2
import aws_cdk.aws_elasticloadbalancingv2 as elbv2


class PythonStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        rule = events.Rule(self, 'Rule', targets=[MyEventTarget()])

        lb = elbv2.ApplicationLoadBalancer(self, 'LB', vpc=ec2.Vpc(self, 'Vpc'))
        listener = lb.add_listener('Listener', port=80)
        listener.add_targets('DefaultTargets', targets=[MyLBTarget()], port=80)


@jsii.implements(events.IRuleTarget)
class MyEventTarget:
  def bind(self, rule, id=None):
    return events.RuleTargetConfig(
      id='asdf',
      arn='target-arn',
      ecs_parameters=events.CfnRule.EcsParametersProperty(task_definition_arn='task-definition-arn')
    )

@jsii.implements(elbv2.IApplicationLoadBalancerTarget)
class MyLBTarget:
  def attach_to_application_target_group(self, tg):
    return elbv2.LoadBalancerTargetProps(
      target_type=elbv2.TargetType.IP,
      target_json={ "port": 1234, "id": "i-1234" }
    )