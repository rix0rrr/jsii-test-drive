package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.ec2.IMachineImage;
import software.amazon.awscdk.services.ec2.MachineImageConfig;
import software.amazon.awscdk.services.ec2.OperatingSystemType;

public class MyMachineImage implements IMachineImage {
    @Override
    public MachineImageConfig getImage(Construct construct) {
        return MachineImageConfig.builder()
                .imageId("ami-12345")
                .osType(OperatingSystemType.LINUX)
                .build();
    }
}
