import ec2 = require('@aws-cdk/aws-ec2');
import cdk = require('@aws-cdk/core');

export class CallbackReturnAnyStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const vpc = ec2.Vpc.fromVpcAttributes(this, "VPC", {
      vpcId: "vpc-987",
      availabilityZones: ['us-west-1a'],
      publicSubnetIds: ['pub-subnet-1'],
    });

    const sg = new ec2.SecurityGroup(this, "SG", { vpc });

    sg.connections.allowFrom(new SomePeer(), ec2.Port.tcp(1));
    sg.connections.allowFrom(new RoundaboutPeer(), ec2.Port.tcp(1));
  }
}

class SomePeer implements ec2.IPeer {
  public readonly connections: ec2.Connections;
  public readonly canInlineRule = true;
  public readonly uniqueId = 'com.myorg.SomePeer';

  public constructor() {
    this.connections = new ec2.Connections({ peer: this });
  }

  public toEgressRuleConfig(): any {
    return {
      cidrIp: '1.2.3.4/5',
      key1: 'val1',
    };
  }

  public toIngressRuleConfig(): any {
    return {
      cidrIp: '2.3.4.5/6',
      key2: 'val2',
    };
  }
}

class RoundaboutPeer implements ec2.IPeer {
  public readonly connections: ec2.Connections;
  public readonly canInlineRule = true;
  public readonly uniqueId = 'com.myorg.RoundaboutPeer';

  public constructor() {
    this.connections = new ec2.Connections({ peer: this });
  }

  public toEgressRuleConfig(): any {
    return {
      cidrIp: cdk.Lazy.anyValue({ produce: () => '3.4.5.6/7' }),
      key3: 'val3',
    };
  }

  public toIngressRuleConfig(): any {
    return {
      cidrIp: cdk.Lazy.anyValue({ produce: () => '4.5.6.7/8' }),
      key4: 'val4',
    };
  }
}
