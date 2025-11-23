package tests;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegisterTests extends TestBase{


    @Test
    @DisplayName("Позитивная регистрация пользователя")
    void successfulRegisterTest() {
        String regData = "{\"email\": \"eve.holt@reqres.in\",\n" + "\"password\": \"cityslicka\"}";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    @DisplayName("При попытке регистрации с пустыми полями должна выводиться 400 ошибка с содержанием \"Empty request body\"")
    void emptyRegisterTest() {
        String regData = "";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Empty request body"))
                .body("message", is("Request body cannot be empty for JSON endpoints"));
    }

    @Test
    @DisplayName("При попытке регистрации без указания пароля должна выводиться 400 ошибка с содержанием \"Missing password\"")
    void notPassswordRegisterTest() {
        String regData = "{\"email\": \"sydney@fife\"}";

        given()
                .header(header)
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
