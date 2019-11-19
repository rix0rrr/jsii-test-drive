#!/usr/bin/env node
import 'source-map-support/register';
import cdk = require('@aws-cdk/core');
import { AspectsStack } from '../lib/aspects-stack';

const app = new cdk.App();
new AspectsStack(app, 'python-aspect');
