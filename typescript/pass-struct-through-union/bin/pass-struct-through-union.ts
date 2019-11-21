#!/usr/bin/env node

import 'source-map-support/register';
import cdk = require('@aws-cdk/core');
import { PassStructThroughUnionStack } from '../lib/pass-struct-through-union-stack';

const app = new cdk.App();
new PassStructThroughUnionStack(app, 'hello-cdk-1');
