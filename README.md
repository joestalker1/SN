# Minimal Triangle Path

The application finds the minimal path in triangle graph.

## Getting Started

You will need to have Sbt, Java 11+.

(`>` prompt):

To create jar,please type in the console
```bash
$ sbt 'clean;assembly'
```

You will see the built jar file in ./target/scala-2.13/MinTrianglePath.jar

In order to run it, please use the following command:
```bash
$ cat << EOF | java -jar ./target/scala-2.13/MinTrianglePath.jar
```

In order to run it with data file use the following command:
```bash
$ cat <data file> | java -jar ../target/scala-2.13/MinTrianglePath.jar
```
