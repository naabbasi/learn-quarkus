package edu.learn;

import edu.learn.entity.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Header;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseOptions;
import org.junit.jupiter.api.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@DisplayName("User Test Cases")
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceTest {

    private static String username;
    private static Integer countUsers;
    private static Header langHeader;

    @BeforeAll
    public static void startup() throws UnknownHostException {
        username = "User" + InetAddress.getLocalHost().getHostName().toLowerCase();
        //langHeader = new Header("Accept-Language", "en-PK");
        //langHeader = new Header("Accept-Language", "es-FR");
        langHeader = new Header("Accept-Language", "ur-PK");
    }

    @DisplayName("Fetch all users")
    @Order(0)
    @Test
    public void allUsersEndpoint() {
        ResponseBody responseBody = given().when().header(langHeader).get("/api/users").getBody();
        List<User> users = responseBody.as(new TypeRef<>() {});
        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());

        countUsers = users.size();
    }

    @DisplayName("Fetch last user based on all users size")
    @Order(1)
    @Test
    public void getUserByIdEndpoint() {
        given().when().header(langHeader).get("/api/users/" + countUsers.intValue() ).andReturn();
    }

    /**
     * Set username with one character to get localized validation error message
     */
    @DisplayName("Save a new user")
    @Order(2)
    @Test
    public void saveUserEndpoint() {
        JsonObject user = Json.createObjectBuilder()
                .add("username", username)
                .add("password", "xxx")
                .add("role", "user")
                .build();

        ResponseOptions responseOptions = given().contentType(MediaType.APPLICATION_JSON)
                .body(user.toString())
                .header(langHeader)
                .when()
                .post("/api/users")
                .andReturn();


        ResponseBody responseBody = responseOptions.body();
        responseBody.peek();

        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseOptions.statusCode());
    }

    @DisplayName("Update a saved user")
    @Order(3)
    @Test
    public void updateUserEndpoint() {
        JsonObject user = Json.createObjectBuilder()
                .add("userId", (countUsers.intValue() + 1))
                .add("username", username)
                .add("password", "xxx1")
                .add("role", "user")
                .build();

        ResponseOptions responseOptions = given().contentType(MediaType.APPLICATION_JSON)
                .body(user.toString())
                .when()
                .put("/api/users/" + (countUsers.intValue() + 1))
                .andReturn();

        ResponseBody responseBody = responseOptions.body();
        responseBody.peek();

        Assertions.assertEquals(Response.Status.ACCEPTED.getStatusCode(), responseOptions.statusCode());
    }

    @DisplayName("Delete last updated user")
    @Order(4)
    @Test
    public void deleteTestUserEndpoint() {
        ResponseOptions responseOptions = given().when().delete("/api/users/" + (countUsers.intValue() + 1)).andReturn();

        ResponseBody responseBody = responseOptions.body();
        responseBody.peek();

        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), responseOptions.statusCode());
    }

    @DisplayName("Fetch last deleted user")
    @Order(5)
    @Test
    public void getDeletedUserByIdEndpoint() {
        ResponseOptions responseOptions = given().when().get("/api/users/" + (countUsers.intValue() + 1)).andReturn();

        ResponseBody responseBody = responseOptions.body();
        responseBody.peek();

        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), responseOptions.statusCode());
    }

    @DisplayName("Fetch all users to compare count of users when tests are started")
    @Order(6)
    @Test
    public void listOfAllUsersEndpoint() {
        given()
          .when().get("/api/users")
          .then()
             .statusCode(200)
             .body("size()", equalTo(countUsers));
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
