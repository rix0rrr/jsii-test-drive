#!/usr/bin/env node
import 'source-map-support/register';
import cdk = require('@aws-cdk/core');
import { AbstractClassStack } from '../lib/abstract-class-stack';

const app = new cdk.App();
new AbstractClassStack(app, 'hello');
