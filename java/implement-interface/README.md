This illustrates implementing an interface from Java.
The implementation works correctly,
but it uncovered a different bug,
with `Vpc.fromVpcAttributes()` not working.
To reproduce:

```shell script
mvn clean package
npx cdk synth
```
