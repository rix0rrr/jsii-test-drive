package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.SecretValue;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.codebuild.Project;
import software.amazon.awscdk.services.codepipeline.Artifact;
import software.amazon.awscdk.services.codepipeline.Pipeline;
import software.amazon.awscdk.services.codepipeline.StageProps;
import software.amazon.awscdk.services.codepipeline.actions.CodeBuildAction;
import software.amazon.awscdk.services.codepipeline.actions.GitHubSourceAction;
import software.amazon.awscdk.services.events.EventPattern;
import software.amazon.awscdk.services.events.IRule;
import software.amazon.awscdk.services.events.IRuleTarget;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.RuleTargetConfig;

import static java.util.Arrays.asList;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        // this doesn't work (the custom CodePipeline action)
        Artifact sourceOutput = new Artifact();
        Pipeline.Builder.create(this, "Pipeline")
                .stages(asList(
                        StageProps.builder()
                                .stageName("Source")
                                .actions(asList(
                                        GitHubSourceAction.Builder.create()
                                                .actionName("GitHub")
                                                .oauthToken(SecretValue.plainText("secret"))
                                                .owner("aws")
                                                .repo("cdk")
                                                .output(sourceOutput)
                                                .build()
                                ))
                                .build(),
                        StageProps.builder()
                                .stageName("Build")
                                .actions(asList(
                                        CodeBuildAction.Builder.create()
                                                .actionName("CodeBuild")
                                                .input(sourceOutput)
                                                .project(Project.fromProjectName(this, "Project", "my-project"))
                                                .build()
                                        ,
                                        new MyCodePipelineAction()
                                ))
                                .build()
                ))
                .build();
    }
}
