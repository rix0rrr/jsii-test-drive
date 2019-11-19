#!/usr/bin/env node
import 'source-map-support/register';
import cdk = require('@aws-cdk/core');
import { ReturnStructFromCallbacksStack } from '../lib/return-struct-from-callbacks-stack';

const app = new cdk.App();
new ReturnStructFromCallbacksStack(app, 'python-aspect');
