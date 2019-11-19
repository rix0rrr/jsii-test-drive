import { expect as expectCDK, matchTemplate, MatchStyle } from '@aws-cdk/assert';
import cdk = require('@aws-cdk/core');
import L1AndOverrides = require('../lib/l1-and-overrides-stack');

test('Empty Stack', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new L1AndOverrides.L1AndOverridesStack(app, 'MyTestStack');
    // THEN
    expectCDK(stack).to(matchTemplate({
      "Resources": {}
    }, MatchStyle.EXACT))
});