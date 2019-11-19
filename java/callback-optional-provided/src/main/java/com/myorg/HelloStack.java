package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.events.EventPattern;
import software.amazon.awscdk.services.events.Rule;

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
                .eventPattern(EventPattern.builder()
                        .source(asList("source"))
                        .build()
                )
                .build();
    }
}
