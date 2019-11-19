#!/usr/bin/env python3

from aws_cdk import core

from python.aspect_stack import AspectStack


app = core.App()
AspectStack(app, "python-aspect")

app.synth()
