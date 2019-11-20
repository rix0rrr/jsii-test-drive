import ec2 = require('@aws-cdk/aws-ec2');
import elb = require('@aws-cdk/aws-elasticloadbalancingv2');
import cdk = require('@aws-cdk/core');

export class ReturnStructFromCallbacksStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const lb = new elb.ApplicationLoadBalancer(this, 'LB', { vpc: new ec2.Vpc(this, 'Vpc') });
    const listener = lb.addListener('Listener', { port: 80 });
    listener.addTargets('DefaultTargets', { targets: [new MyLBTarget()], port: 80 });
  }
}

class MyLBTarget implements elb.IApplicationLoadBalancerTarget {
  public attachToApplicationTargetGroup(targetGroup: elb.IApplicationTargetGroup): elb.LoadBalancerTargetProps {
    return {
      targetType: elb.TargetType.IP,
      targetJson: { port: 1234, id: 'i-1234' }
    };
  }
}
