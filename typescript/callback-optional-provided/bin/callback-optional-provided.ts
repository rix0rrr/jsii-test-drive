#!/usr/bin/env node

import 'source-map-support/register';
import cdk = require('@aws-cdk/core');
import { CallbackOptionalProvidedStack } from '../lib/callback-optional-provided-stack';

const app = new cdk.App();
new CallbackOptionalProvidedStack(app, 'hello-cdk-1');
