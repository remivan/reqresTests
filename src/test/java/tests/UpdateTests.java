package tests;

import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateTests {

    Header header = new Header("x-api-key", "reqres-free-v1");

    @Test
    void successfulUpdateTest() {
        String regData = "{\"name\": \"morpheus\",\n" + " \"job\": \"zion resident\"}";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("https://reqres.in/api/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", notNullValue());
    }
}
