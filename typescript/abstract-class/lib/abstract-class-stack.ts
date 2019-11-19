import lambda = require('@aws-cdk/aws-lambda');
import cdk = require('@aws-cdk/core');

let wasCalled = false;

export class AbstractClassStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // The code that defines your stack goes here
    new lambda.Function(this, 'Lamna', {
      handler: 'index.handler',
      runtime: lambda.Runtime.DOTNET_CORE_1,
      code: new MyCode(),
    });

    new lambda.LayerVersion(this, 'LV1', {
      code: new MyCode(),
      compatibleRuntimes: [lambda.Runtime.DOTNET_CORE_1],
    });

    if (!wasCalled) {
      throw new Error('It was not called!');
    }
  }
}

class MyCode extends lambda.Code {
  public bind(scope: cdk.Construct): lambda.CodeConfig {
    const fakeyResource = new cdk.CfnResource(scope, 'Bogus', { type: 'AWS::Nothing::Bye' });
    this.bindToResource(fakeyResource);
    return { s3Location: { bucketName: 'Henk', objectKey: 'Doei.zip' } };
  }

  public get isInline(): false {
    wasCalled = true;
    return false;
  }
}
