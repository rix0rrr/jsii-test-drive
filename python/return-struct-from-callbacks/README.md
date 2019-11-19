Implement `IEventRuleTarget` and `IApplicationLoadBalancerTarget`
from Python. Doesn't properly serialize the return struct:

```
TypeError: Don't know how to convert object to JSON: RuleTargetConfig(arn='target-arn', id='asdf', ecs_parameters=EcsParametersProperty(task_definition_arn='task-definition-arn'))
```

Reported as https://github.com/aws/jsii/issues/1007.

Use:

```
python3 -m venv venv
pip install -r requirements.txt
cdk synth
```
