#!/usr/bin/env python3

from aws_cdk import core

from python.python_stack import PythonStack


app = core.App()
PythonStack(app, "python-aspect")

assembly = app.synth()

# Get an untyped object through 'any'
print(assembly.get_stack("python-aspect").template)
