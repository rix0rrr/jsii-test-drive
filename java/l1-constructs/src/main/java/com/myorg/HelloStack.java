package com.myorg;

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
                .bucketName("my-bucket-name")
                .accelerateConfiguration(
                        new CfnBucket.AccelerateConfigurationProperty.Builder()
                                .accelerationStatus("enabled")
                                .build()
                )
                .build();

        bucket.addPropertyOverride("BucketName", "actual-bucket-name");

        Key key = new Key(this, "Key");
        CfnResource cfnKey = (CfnResource) key.getNode().getDefaultChild();
        cfnKey.addPropertyOverride("Description",
                "description for KMS key, added from override");
    }
}
