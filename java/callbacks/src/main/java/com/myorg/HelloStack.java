package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ecs.Compatibility;
import software.amazon.awscdk.services.ecs.ContainerDefinition;
import software.amazon.awscdk.services.ecs.LinuxParameters;
import software.amazon.awscdk.services.ecs.TaskDefinition;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        // tests a callback getting a parameter be null
        ContainerDefinition.Builder.create(this, "ContainerDef1")
                .taskDefinition(
                        TaskDefinition.Builder.create(this, "TaskDef1")
                                .compatibility(Compatibility.EC2_AND_FARGATE)
                                .memoryMiB("1024MiB")
                                .cpu("24")
                                .build()
                )
                .image(new MyContainerImageNoLinuxParams())
                .memoryLimitMiB(1024)
                .build();

        // tests a callback getting a parameter being non-null
        ContainerDefinition.Builder.create(this, "ContainerDef2")
                .taskDefinition(
                        TaskDefinition.Builder.create(this, "TaskDef2")
                                .compatibility(Compatibility.EC2_AND_FARGATE)
                                .memoryMiB("1024MiB")
                                .cpu("24")
                                .build()
                )
                .image(new MyContainerImageLinuxParams())
                .linuxParameters(
                        LinuxParameters.Builder.create(this, "LinuxParameters")
                            .initProcessEnabled(true)
                            .build()
                )
                .memoryLimitMiB(1024)
                .build();
    }
}
