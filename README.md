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

```
mvn git-code-format:validate-code-format -Dgcf.globPattern=**/*
```

To format the code:

```
mvn git-code-format:format-code -Dgcf.globPattern=**/*
```

[Style Guide]: https://google.github.io/styleguide/javaguide.html
[Plugin]: https://github.com/Cosium/git-code-format-maven-plugin
