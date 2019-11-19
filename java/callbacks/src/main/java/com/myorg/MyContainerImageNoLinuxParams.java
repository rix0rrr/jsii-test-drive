package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.ecs.ContainerDefinition;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.ContainerImageConfig;

public class MyContainerImageNoLinuxParams extends ContainerImage {
    @Override
    public ContainerImageConfig bind(Construct construct, ContainerDefinition containerDefinition) {
        if (containerDefinition.getLinuxParameters() != null) {
            throw new RuntimeException("Expected containerDefinition.linuxParameters to be null, was: " +
                    containerDefinition.getLinuxParameters());
        }
        return ContainerImageConfig.builder()
                .imageName("my-no-linux-params-image")
                .build();
    }
}
