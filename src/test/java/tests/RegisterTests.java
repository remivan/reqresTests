package tests;


import models.RegistrationErrorModel;
import models.RegistrationBodyModel;
import models.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegistrationSpec.*;

public class RegisterTests extends TestBase{


    @Test
    @DisplayName("Позитивная регистрация пользователя")
    void successfulRegisterTest() {

        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("cityslicka");

        RegistrationResponseModel response = step("Make request", ()->
            given(registrationRequestSpec)
                    .body(regData)

                    .when()
                    .post("/register")

                    .then()

                    .spec(registrationResponseSpec)
                    .extract().as(RegistrationResponseModel.class));

        step("Check response", ()-> {
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
                assertEquals("4", response.getId());
        });
    }

    @Test
    @DisplayName("При попытке регистрации с пустыми полями должна выводиться 400 ошибка с содержанием \"Empty request body\"")
    void emptyRegisterTest() {

        RegistrationErrorModel response = step("Make request", ()->
                given(registrationRequestSpec)

                .when()
                .post("/register")

                .then()
                .spec(registrationEmptyResponseSpec)
                .extract().as(RegistrationErrorModel.class));

        step("Check response", ()-> {
                assertEquals("Empty request body", response.getError());
                assertEquals("Request body cannot be empty for JSON endpoints", response.getMessage());
        });
    }

    @Test
    @DisplayName("При попытке регистрации без указания пароля должна выводиться 400 ошибка с содержанием \"Missing password\"")
    void notPassswordRegisterTest() {

        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail("sydney@fife");
        regData.setPassword("");

        RegistrationErrorModel response = step("Make request", ()->
                given(registrationRequestSpec)
                .body(regData)

                .when()
                .post("/register")

                .then()
                .spec(missingPasswordResponseSpec)
                .extract().as(RegistrationErrorModel.class));

        step("Check response", ()-> {
                assertEquals("Missing password", response.getError());
        });
    }
}
