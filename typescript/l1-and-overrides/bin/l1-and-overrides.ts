#!/usr/bin/env node
import 'source-map-support/register';
import cdk = require('@aws-cdk/core');
import { L1AndOverridesStack } from '../lib/l1-and-overrides-stack';

const app = new cdk.App();
new L1AndOverridesStack(app, 'python-aspect');

const assembly = app.synth();

console.log(JSON.stringify(assembly.getStack("python-aspect").template, null, 2));
