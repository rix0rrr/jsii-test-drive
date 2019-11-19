package com.myorg;

import java.util.Collections;

import software.amazon.awscdk.core.CfnResource;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.kms.Key;
import software.amazon.awscdk.services.s3.CfnBucket;

public class HelloStack extends Stack {
    public HelloStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HelloStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        CfnBucket bucket = CfnBucket.Builder.create(this, "Bucket")
                .bucketEncryption(new CfnBucket.BucketEncryptionProperty.Builder()
                    .serverSideEncryptionConfiguration(Collections.singletonList(CfnBucket.ServerSideEncryptionRuleProperty.builder()
                            .serverSideEncryptionByDefault(new CfnBucket.ServerSideEncryptionByDefaultProperty.Builder()
                                    .kmsMasterKeyId("the-kms-key-id")
                                    .sseAlgorithm("aws:kms")
                                    .build())))
                    .build())
                .build();
    }
}
