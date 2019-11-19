package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.autoscaling.AutoScalingGroup;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcAttributes;

import static software.amazon.awscdk.core.Token.asList;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        IVpc vpc = Vpc.fromVpcAttributes(this, "VPC", VpcAttributes.builder()
                .vpcId("vpc-987")
                .availabilityZones(asList("us-west-1a"))
                .publicSubnetIds(asList("pub-subnet-1"))
                .build());

        AutoScalingGroup.Builder.create(this, "ASG")
                .vpc(vpc)
                .instanceType(InstanceType.of(InstanceClass.COMPUTE5, InstanceSize.SMALL))
//                .machineImage(new AmazonLinuxImage())
                // the above works fine - uncomment that,
                // and comment out the below line,
                // to verify
                .machineImage(new MyMachineImage())
                .build();
    }
}
