# NappMusic

An test app for listening to music.

## Compiling

### Dependencies

We use a JAR file created by our professors for this project
and some external dependencies.
If you want to compile this project,
check the [parent repository].

[parent repository]: https://github.com/useredsa/TDS-DominguezSanchezEmilio-GasparMarcoRuben

## Contributing

[Style Guide].

## Code format plugin

[Plugin].

To install a git pre-commit hook for auto formatting:

```sh
mvn git-code-format:install-hooks
```

To validate the code format:

```sh
mvn git-code-format:validate-code-format -Dgcf.globPattern=**/*
```

To format the code:

```sh
mvn git-code-format:format-code -Dgcf.globPattern=**/*
```

[Style Guide]: https://google.github.io/styleguide/javaguide.html
[Plugin]: https://github.com/Cosium/git-code-format-maven-plugin

## Building a standalone JAR

The following command will copy the dependencies
to the folder `target/dependency/`
and create an jar `target/NappMusic-1.0-SNAPSHOT.jar`
with its classpath variable set to look inside `dependency`.

```sh
mvn clean dependency:copy-dependencies package
```

Run the application with

```java
java -jar target/NappMusic-1.0-SNAPSHOT.jar
```
