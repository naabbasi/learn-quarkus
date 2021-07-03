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
mvn quarkus:add-extension -Dextensions="quarkus-security-jpa"
mvn quarkus:add-extension -Dextensions="quarkus-security-jpa"

## Quarkus extension
mvn quarkus:list-extensions
mvn quarkus:add-extension -Dextensions="quarkus-jackson,quarkus-agroal"
mvn quarkus:add-extension -Dextensions="quarkus-security-jpa"
mvn quarkus:add-extension -Dextensions="quarkus-test-security"
mvn quarkus:add-extension -Dextensions="smallrye-jwt, smallrye-jwt-build"

### To enable login via JPA, Please follow the following steps:
#### Enable login page
quarkus.http.auth.form.enabled=true
### Login html
<pre>
    <form method="post" action="j_security_check">
        <input type="text" name="j_username" required>
        <input type="password" name="j_password" required>
        <input type="submit" value="Login">
    </form>
</pre>
### User entity
<pre>
package edu.learn.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.StringJoiner;

@UserDefinition
@Entity
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long userId;

    @Username
    @Length(min = 3, max = 255, message = "{invalid.length.message}")
    public String username;

    @Password
    @Length(min = 3, max = 255, message = "{invalid.length.message}")
    public String password;

    @Roles
    @Length(min = 3, max = 255, message = "{invalid.length.message}")
    public String role;
}
</pre>
## User repository
<pre>
import edu.learn.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@Transactional
@RequestScoped
public class UserRepository implements PanacheRepository<User> {
    public User saveOrUpdate(User user) {
        User getUser = null;

        if(user.getUserId() != null) {
            getUser = this.findById(user.getUserId());
        } else {
            getUser = new User();
        }

        getUser.setUsername(user.getUsername());
        getUser.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        getUser.setRole(user.getRole());
        persist(getUser);

        this.isPersistent(getUser);

        return getUser;
    }

    public void deleteUser(Long userId) {
        User getUser = this.findById(userId);
        this.delete(getUser);
    }
}
</pre>

## Generate a certificate/private key for JWT (resource folder)
openssl req -new -newkey rsa:2048 -nodes -keyout server.key -out server.csr  -subj “/C=PK/ST=Sind/L=Karachi/O=Matrix Systems pvt ltd/OU=Software Development/CN=nabbasi”
#### Sign the certificate
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
#### Generate a certificate as pkcs12 format
openssl pkcs12 -export -in server.crt -inkey server.key -out server.p12 -name quarkus-ssl -CAfile server.crt -caname root -chain
### Public Key
openssl rsa -in server.key -pubout > publickey.pem

### Generate token via GenerateToken.java
export token=

curl -H "Authorization: Bearer $token" -v https://noman:8443/api/users/
curl -H "Authorization: Bearer $token" -v https://noman:8443/api/users/1
curl -H "Authorization: Bearer $token" -v https://noman:8443/api/users/permit-all
curl -H "Authorization: Bearer $token" -v https://noman:8443/api/users/roles

set token=
curl -H "Authorization: Bearer %token%" -v https://noman:8443/api/users/
curl -H "Authorization: Bearer %token%" -v https://noman:8443/api/users/1
curl -H "Authorization: Bearer %token%" -v https://noman:8443/api/users/permit-all
curl -H "Authorization: Bearer %token%" -v https://noman:8443/api/users/roles

