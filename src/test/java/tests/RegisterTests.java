package tests;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegisterTests {

    Header header = new Header("x-api-key", "reqres-free-v1");


    @Test
    void successfulRegisterTest() {
        String regData = "{\"email\": \"eve.holt@reqres.in\",\n" + "\"password\": \"cityslicka\"}";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    void emptyRegisterTest() {
        String regData = "";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Empty request body"))
                .body("message", is("Request body cannot be empty for JSON endpoints"));
    }

    @Test
    void notPassswordRegisterTest() {
        String regData = "{\"email\": \"sydney@fife\"}";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
