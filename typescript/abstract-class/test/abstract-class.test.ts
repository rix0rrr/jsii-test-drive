import { expect as expectCDK, matchTemplate, MatchStyle } from '@aws-cdk/assert';
import cdk = require('@aws-cdk/core');
import AbstractClass = require('../lib/abstract-class-stack');

test('Empty Stack', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new AbstractClass.AbstractClassStack(app, 'MyTestStack');
    // THEN
    expectCDK(stack).to(matchTemplate({
      "Resources": {}
    }, MatchStyle.EXACT))
});