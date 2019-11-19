package com.myorg;

import software.amazon.awscdk.services.events.IRule;
import software.amazon.awscdk.services.events.IRuleTarget;
import software.amazon.awscdk.services.events.RuleTargetConfig;

public class MyEventTarget implements IRuleTarget {
    @Override
    public RuleTargetConfig bind(IRule rule) {
        throw new RuntimeException("Expected bind(IRule, String) to be called!");
    }

    @Override
    public RuleTargetConfig bind(IRule rule, String id) {
        if (id == null) {
            throw new RuntimeException("Expected id to be not null!");
        }
        return RuleTargetConfig.builder()
                .arn("arn")
                .id(id)
                .build();
    }
}
