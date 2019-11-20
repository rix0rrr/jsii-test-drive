#!/usr/bin/env node

import 'source-map-support/register';
import cdk = require('@aws-cdk/core');
import { CallbackReturnAnyStack } from '../lib/callback-return-any-stack';

const app = new cdk.App();
new CallbackReturnAnyStack(app, 'hello-cdk-1');
