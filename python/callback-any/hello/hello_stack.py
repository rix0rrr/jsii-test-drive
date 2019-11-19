import jsii
from aws_cdk import core
import aws_cdk.aws_ec2 as ec2
import sys


class HelloStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        vpc = ec2.Vpc(self, 'Vpc')

        sg = ec2.SecurityGroup(self, 'SG', vpc=vpc)
        sg.connections.allow_from(SomeIP(), ec2.Port.tcp(1))
        sg.connections.allow_from(RoundaboutIP(), ec2.Port.tcp(1))


@jsii.implements(ec2.IPeer)
class SomeIP:
  def __init__(self):
    self._connections = ec2.Connections(peer=self)

  @property
  def connections(self):
    return self._connections

  @property
  def unique_id(self):
    return self.__class__.__name__

  @property
  def can_inline_rule(self):
    return True

  def to_ingress_rule_config(self):
    return dict(
        cidrIp='1.2.3.4/5',
        somethingElse='bye')


@jsii.implements(ec2.IPeer)
class RoundaboutIP:
  def __init__(self):
    self._connections = ec2.Connections(peer=self)

  @property
  def connections(self):
    return self._connections

  @property
  def unique_id(self):
    return self.__class__.__name__

  @property
  def can_inline_rule(self):
    return True

  def to_ingress_rule_config(self):
    return dict(
        cidrIp=core.Lazy.any_value(Roundabout('1.2.3.4/5')))


@jsii.implements(core.IAnyProducer)
class Roundabout:
  def __init__(self, value):
    self.value = value

  def produce(self, context):
    return self.value
