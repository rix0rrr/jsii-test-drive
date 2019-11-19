package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.codepipeline.ActionArtifactBounds;
import software.amazon.awscdk.services.codepipeline.ActionBindOptions;
import software.amazon.awscdk.services.codepipeline.ActionCategory;
import software.amazon.awscdk.services.codepipeline.ActionConfig;
import software.amazon.awscdk.services.codepipeline.ActionProperties;
import software.amazon.awscdk.services.codepipeline.IAction;
import software.amazon.awscdk.services.codepipeline.IStage;
import software.amazon.awscdk.services.events.IRuleTarget;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.RuleProps;

import java.util.HashMap;

public class MyCodePipelineAction implements IAction {
    @Override
    public ActionProperties getActionProperties() {
        return ActionProperties.builder()
                .actionName("MyAction")
                .artifactBounds(ActionArtifactBounds.builder()
                        .minInputs(0)
                        .maxInputs(0)
                        .minOutputs(0)
                        .maxOutputs(0)
                        .build()
                )
                .category(ActionCategory.BUILD)
                .provider("random")
                .build();
    }

    @Override
    public ActionConfig bind(Construct scope, IStage stage, ActionBindOptions options) {
        return ActionConfig.builder()
                .configuration(new HashMap<String, Object>() {{
                    put("PropString", "Value1");
                    put("PropInt", 123);
                }})
                .build();
    }

    @Override
    public Rule onStateChange(String name, IRuleTarget target, RuleProps options) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Rule onStateChange(String name, IRuleTarget target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Rule onStateChange(String name) {
        throw new UnsupportedOperationException();
    }
}
