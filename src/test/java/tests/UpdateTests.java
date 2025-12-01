package tests;


import models.UpdateBodyModel;
import models.UpdateResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.BaseSpecs.requestSpec;
import static specs.BaseSpecs.responseSpecification;


public class UpdateTests extends TestBase {


    @Test
    @Tag("reqres")
    @DisplayName("Успешное редактирование данных пользователя")
    void successfulUpdateTest() {

        UpdateBodyModel updateData = new UpdateBodyModel("morpheus", "zion resident");

        UpdateResponseModel response = step("Make request", ()->
                given(requestSpec)

                .body(updateData)

                .when()
                .patch("/users/2")

                .then()
                .spec(responseSpecification(200))
                .extract().as(UpdateResponseModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", response.getName());
            assertEquals("zion resident", response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }
}
