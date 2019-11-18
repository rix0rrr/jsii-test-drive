package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.autoscaling.AutoScalingGroup;
import software.amazon.awscdk.services.ec2.AmazonLinuxImage;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.Vpc;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Vpc vpc = new Vpc(this, "VPC");

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
