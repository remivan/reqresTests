package tests;

import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateTests {
    Header header = new Header("x-api-key", "reqres-free-v1");

    @Test
    void successfulCreateTest() {
        String regData = "{\"name\": \"morpheus\",\n" + "\"job\": \"leader\"}";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    void emptyCreateTest() {

        given()
                .header(header)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Empty request body"))
                .body("message", is("Request body cannot be empty for JSON endpoints"));
    }
}
