This shows a bug with a callback that takes an optional parameter,
and should be called with that parameter passed.
The following TypeScript code works:

```typescript
import { Construct, Stack } from '@aws-cdk/core';
import events = require('@aws-cdk/aws-events');

export class MyStack extends Stack {
    constructor(scope: Construct, id: string) {
        super(scope, id);

        new events.Rule(this, 'Rule', {
            targets: [
                new MyTarget(),
            ],
            eventPattern: {
                source: ['my-source'],
            },
        });
    }
}

class MyTarget implements events.IRuleTarget {
    public bind(rule: events.IRule, id?: string): events.RuleTargetConfig {
        if (!id) {
            throw new Error("Expected 'id' to be passed!");
        }
        return {
            arn: 'arn',
            id: '',
        };
    }
}
```

To reproduce:

```shell script
mvn clean package && npx cdk synth
```
