package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateTests extends TestBase{


    @Test
    @DisplayName("Позитивное создание нового пользователя")
    void successfulCreateTest() {
        String regData = "{\"name\": \"morpheus\",\n" + "\"job\": \"leader\"}";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/users")

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
    @DisplayName("При попытке регистрации с пустыми полями должна выводиться 400 ошибка с содержанием \"Empty request body\"")
    void emptyCreateTest() {

        given()
                .header(header)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Empty request body"))
                .body("message", is("Request body cannot be empty for JSON endpoints"));
    }
}
