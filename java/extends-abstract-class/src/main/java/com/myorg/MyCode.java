package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.CodeConfig;
import software.amazon.awscdk.services.s3.Location;

public class MyCode extends Code {
    @Override
    public CodeConfig bind(Construct construct) {
        return CodeConfig.builder()
                .s3Location(Location.builder()
                        .bucketName("code-bucket")
                        .objectKey("object-key")
                        .build()
                )
                .build();
    }
}
