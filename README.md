## Micronaut 3.4.2 Documentation

- [User Guide](https://docs.micronaut.io/3.4.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.4.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.4.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
## Handler

[AWS Lambda Handler](https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html)

Handler: mis055.BookRequestHandler


## Deployment with GraalVM

If you want to deploy to AWS Lambda as a GraalVM native image, run:

```bash
./gradlew buildNativeLambda -Pmicronaut.runtime=lambda
```

This will build the GraalVM native image inside a docker container and generate the `function.zip` ready for the deployment.


## Feature aws-parameter-store documentation

- [Micronaut AWS Parameter Store Distributed Configuration documentation](https://micronaut-projects.github.io/micronaut-aws/latest/guide/index.html#parametersStore)

- [https://docs.aws.amazon.com/systems-manager/latest/userguide/systems-manager-parameter-store.html](https://docs.aws.amazon.com/systems-manager/latest/userguide/systems-manager-parameter-store.html)


## Feature aws-lambda documentation

- [Micronaut AWS Lambda Function documentation](https://micronaut-projects.github.io/micronaut-aws/latest/guide/index.html#lambda)


## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)


## Feature aws-lambda-custom-runtime documentation

- [Micronaut Custom AWS Lambda runtime documentation](https://micronaut-projects.github.io/micronaut-aws/latest/guide/index.html#lambdaCustomRuntimes)

- [https://docs.aws.amazon.com/lambda/latest/dg/runtimes-custom.html](https://docs.aws.amazon.com/lambda/latest/dg/runtimes-custom.html)


