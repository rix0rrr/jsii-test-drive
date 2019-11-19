import { expect as expectCDK, matchTemplate, MatchStyle } from '@aws-cdk/assert';
import cdk = require('@aws-cdk/core');
import ReturnStructFromCallbacks = require('../lib/return-struct-from-callbacks-stack');

test('Empty Stack', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new ReturnStructFromCallbacks.ReturnStructFromCallbacksStack(app, 'MyTestStack');
    // THEN
    expectCDK(stack).to(matchTemplate({
      "Resources": {}
    }, MatchStyle.EXACT))
});