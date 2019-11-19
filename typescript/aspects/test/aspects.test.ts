import { expect as expectCDK, matchTemplate, MatchStyle } from '@aws-cdk/assert';
import cdk = require('@aws-cdk/core');
import Aspects = require('../lib/aspects-stack');

test('Empty Stack', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new Aspects.AspectsStack(app, 'MyTestStack');
    // THEN
    expectCDK(stack).to(matchTemplate({
      "Resources": {}
    }, MatchStyle.EXACT))
});