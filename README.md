## Example Project to demonstrate a micronaut-openapi bug with placeholder expansion

### Step To Reproduce

1. Clone the [example code repo](https://github.com/sparrowlegs/broken-micronaut-openapi-expand)
2. Change to the project directory
3. Run `./mvnw verify`
4. The unit test failure demonstrates that the placeholder values are not expand properly

Notice in the build output that the placeholders are correctly identified (line 2). Also notice that the `api.version` placeholder value is correctly expanded into the YAML resource file name on line 3.

```
[INFO] Generating OpenAPI Documentation
[INFO] Expanding properties: [${api.version}=2.2.2, ${another.placeholder.value}=monkey]
[INFO] Writing OpenAPI file to destination: ./target/classes/META-INF/swagger/broken-micronaut-openapi-expand-2.2.2.yml
[INFO] Writing OpenAPI views to destination: ./target/classes/META-INF/swagger/views
```

5. Inspect the contents of the generated resource file: `./target/classes/META-INF/swagger/broken-micronaut-openapi-expand-2.2.2.yml`. Notice that the placeholder values are not expanded.

```YAML
openapi: 3.0.1
info:
  title: broken-micronaut-openapi-expand
  description: "${another.placeholder.value}"
  version: "${api.version}"
``` 