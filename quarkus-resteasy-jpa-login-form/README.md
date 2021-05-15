# Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev

## Packaging and running the application
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

mvn quarkus:list-extensions
mvn quarkus:add-extension -Dextensions="quarkus-jackson,quarkus-agroal"

#Add the following dependency to enable support for multi form data
mvn quarkus:add-extension -Dextensions="quarkus-resteasy-multipart"

#Authenticate the request using j_security_check
To submit a request on j_security_check, Please add the following on an entity (once)
mvn quarkus:add-extension -Dextensions="quarkus-security-jpa"

<pre>
    @UserDefinition
    @Entity
    public class User extends PanacheEntityBase {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userId;
        @Username
        private String username;
        @Password
        private String password;
        @Roles
        private String role;
    }
</pre>

login.html
<form method="post" action="j_security_check">
    <input type="text" id="j_username" name="j_username" required>
    <input type="password" id="j_password" name="j_password" required>
    <input type="submit" value="Login">
</form>-

Skip security in test add the following
<dependency>
<groupId>io.quarkus</groupId>
<artifactId>quarkus-test-security</artifactId>
<scope>test</scope>
</dependency>
