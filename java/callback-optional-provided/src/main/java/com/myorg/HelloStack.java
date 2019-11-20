package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.events.CfnRule;
import software.amazon.awscdk.services.events.CronOptions;
import software.amazon.awscdk.services.events.IRule;
import software.amazon.awscdk.services.events.IRuleTarget;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.RuleTargetConfig;
import software.amazon.awscdk.services.events.Schedule;

import static java.util.Arrays.asList;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        // tests a callback getting an optional parameter be non-null
        Rule.Builder.create(this, "Rule")
                .targets(asList(new MyEventTarget()))
                .schedule(Schedule.cron(CronOptions.builder()
                        .day("20")
                        .build())
                )
                .build();
    }
}

class MyEventTarget implements IRuleTarget {
    @Override
    public RuleTargetConfig bind(IRule rule) {
//        return this.bind(rule, "dummy");
        throw new RuntimeException("Expected bind(IRule, String) to be called!");
    }

    @Override
    public RuleTargetConfig bind(IRule rule, String id) {
        if (id == null) {
            throw new RuntimeException("Expected id to be not null!");
        }
        return RuleTargetConfig.builder()
                .id("asdf")
                .arn("target-arn")
                .ecsParameters(CfnRule.EcsParametersProperty.builder()
                        .taskDefinitionArn("task-definition-arn")
                        .build()
                )
                .build();
    }
}
