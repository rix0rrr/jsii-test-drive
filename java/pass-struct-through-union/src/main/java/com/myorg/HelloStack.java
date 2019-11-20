package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.sam.CfnFunction;

import java.util.HashMap;
import java.util.Map;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        CfnFunction fun = CfnFunction.Builder.create(this, "Fn")
                .codeUri("asdf")
                .handler("index.handler")
                .runtime("runtime")
                .events(new HashMap<String, Object>() {{
                    put("Event", CfnFunction.EventSourceProperty.builder()
                            .type("type1")
                            .properties(CfnFunction.SQSEventProperty.builder()
                                    .queue("queue-arn")
                                    .build()
                            )
                            .build()
                    );
                }})
                .build();

//        Map<String, CfnFunction.EventSourceProperty> events = (Map<String, CfnFunction.EventSourceProperty>) fun.getEvents();
//        System.out.println(events.get("Event").getProperties());
    }
}
