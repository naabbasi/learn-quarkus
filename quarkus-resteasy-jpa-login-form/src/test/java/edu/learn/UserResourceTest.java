package edu.learn;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


@DisplayName("User Test Cases")
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestSecurity(user = "nabbasi", roles = {"admin", "user"})
public class UserResourceTest {

    @Order(1)
    @Test
    public void saveUserEndpoint() {
        JsonObject user = Json.createObjectBuilder()
                .add("username", "naabbasi")
                .add("password", "x")
                .add("role", "user")
                .build();

        given().contentType(MediaType.APPLICATION_JSON)
                .body(user.toString())
                .when()
                .post("/api/users")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Order(2)
    @Test
    public void updateUserEndpoint() {
        JsonObject user = Json.createObjectBuilder()
                .add("userId", 3)
                .add("username", "naabbasi")
                .add("password", "x1")
                .add("role", "user")
                .build();

        given().contentType(MediaType.APPLICATION_JSON)
                .body(user.toString())
                .when()
                .put("/api/users/3")
                .then()
                .statusCode(Response.Status.ACCEPTED.getStatusCode());
    }

    @Order(3)
    @Test
    //@TestSecurity(authorizationEnabled = false)
    public void allUsersEndpoint() {
        given()
          .when().get("/api/users")
          .then()
             .statusCode(200)
             .body("size()", equalTo(3));
    }

    @Order(4)
    @Test
    public void findUserByIdEndpoint() {
        given()
                .when().get("/api/users/3")
                .then()
                .statusCode(200)
                .body("username", equalTo("naabbasi"))
                .body("password", equalTo("x1"));
    }

    /*@Order(3)
    @DisplayName("Get all users")
    @Test
    public void testHelloEndpoint1() {
        ExtractableResponse<io.restassured.response.Response> extract = given()
                .when().contentType(ContentType.JSON).get("/api/users")
                .then()
                .statusCode(200)
                .extract();

        ResponseBodyExtractionOptions extractUser = extract.body();
        User user = extractUser.as(User.class);

        assertEquals("nabbasi", user.username);
        assertEquals("x", user.password);
    }*/

}
