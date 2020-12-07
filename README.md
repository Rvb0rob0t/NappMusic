# NappMusic

An test app for listening to music.

## Compiling

### DriverPersistencia dependency

We use a JAR file created by our professors for this project.
If you want to compile this project,
you will need to install it in your local maven repository with

```sh
mvn install:install-file -Dfile=lib/DriverPersistencia.jar -DgroupId=um.tds -DartifactId=DriverPersistencia -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
```

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
