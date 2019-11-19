This tests S3 Bucket Encryption, which fails because
of a Jackson serialization error.

Reported as https://github.com/aws/jsii/issues/1006


```
mvn package
cdk synth
```
