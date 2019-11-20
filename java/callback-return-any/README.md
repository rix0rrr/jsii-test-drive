This shows an example of a callback that returns `any`
(`Object` in Java) to the JSII runtime.
Combining it with a lazy value causes a JSII exception.
To reproduce:

```shell script
mvn clean package && npx cdk synth
```
