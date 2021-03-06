# Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-getting-started-1.0-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Quarkus extension
mvn quarkus:list-extensions
mvn quarkus:add-extension -Dextensions="quarkus-jackson,quarkus-agroal"
mvn quarkus:add-extension -Dextensions="quarkus-hibernate-validator"

## Generate a certificate
openssl req -new -newkey rsa:2048 -nodes -keyout server.key -out server.csr  -subj “/C=PK/ST=Sind/L=Karachi/O=Matrix Systems pvt ltd/OU=Software Development/CN=nabbasi”
#### Sign the certificate
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
#### Generate a certificate as pkcs12 format
openssl pkcs12 -export -in server.crt -inkey server.key -out server.p12 -name quarkus-ssl -CAfile server.crt -caname root -chain
