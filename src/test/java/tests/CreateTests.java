package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.BaseSpecs.*;

public class CreateTests extends TestBase{


    @Test
    @Tag("reqres")
    @DisplayName("Позитивное создание нового пользователя")
    void successfulCreateTest() {

        CreateBodyModel createData = new CreateBodyModel("morpheus", "leader");

        CreateResponseModel response = step("Make request", ()->
                given(requestSpec)
                .body(createData)


                .when()
                .post("/users")

                .then()
                .spec(responseSpecification(201))
                .extract().as(CreateResponseModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    @Tag("reqres")
    @DisplayName("При попытке регистрации с пустыми полями должна выводиться 400 ошибка с содержанием \"Empty request body\"")
    void emptyCreateTest() {

        CreateErrorModel response = step("Make request", ()->
                given(requestSpec)

                .when()
                .post("/users")

                .then()
                .spec(responseSpecification(400))
                .extract().as(CreateErrorModel.class));

        step("Check response", ()-> {
            assertEquals("Empty request body", response.getError());
            assertEquals("Request body cannot be empty for JSON endpoints", response.getMessage());
        });
    }
}
