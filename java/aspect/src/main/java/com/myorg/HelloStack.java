package com.myorg;

import software.amazon.awscdk.core.ArnComponents;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.IAspect;
import software.amazon.awscdk.core.IConstruct;
import software.amazon.awscdk.core.RemovalPolicy;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.sns.CfnTopic;
import software.amazon.awscdk.services.sns.Topic;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Topic.Builder.create(this, "Topic")
                .displayName("xxx")
                .build();

        this.getNode().applyAspect(new MyAspect());
    }
}

class MyAspect implements IAspect {
    private int counter = 0;

    @Override
    public void visit(IConstruct construct) {
        System.out.println("----");
        System.out.println(construct);
        System.out.println(construct.getNode().tryFindChild("Resource"));

        // Do a silly thing. We KNOW what order we're visiting
        // constructs in, so we know the types of the things we're
        // expecting. No type tests, just accesses!
        this.counter += 1;

        if (this.counter == 1) {
            // Stack
            Stack stack = (Stack) construct;
            System.out.println(stack.getStackName());
            System.out.println(stack.formatArn(ArnComponents.builder()
                    .region("here")
                    .service("there")
                    .resource("thing")
                    .build()));
        }

        if (this.counter == 2) {
            // Topic
            Topic topic = (Topic) construct;
            System.out.println(topic.getTopicArn());
            topic.grantPublish(new ServicePrincipal("doesnotexist.amazonaws.com"));
        }

        if (this.counter == 3) {
            // CfnTopic
            CfnTopic cfnTopic = (CfnTopic) construct;
            System.out.println(cfnTopic.getCfnResourceType());
            cfnTopic.applyRemovalPolicy(RemovalPolicy.RETAIN);
            cfnTopic.addDependsOn(cfnTopic); // This will make it undeployable
            if (!cfnTopic.getDisplayName().equals("xxx")) {
                throw new AssertionError("Expected cfnTopic.displayName to be 'xxx', but was: " + cfnTopic.getDisplayName());
            }
            cfnTopic.setDisplayName("yyy");
        }
    }
}
