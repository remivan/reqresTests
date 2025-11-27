package tests;


import models.UpdateBodyModel;
import models.UpdateResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.UpdateSpec.updateRequestSpec;
import static specs.UpdateSpec.updateResponseSpec;

public class UpdateTests extends TestBase {


    @Test
    @DisplayName("Успешное редактирование данных пользователя")
    void successfulUpdateTest() {

        UpdateBodyModel updateData = new UpdateBodyModel();
        updateData.setName("morpheus");
        updateData.setJob("zion resident");

        UpdateResponseModel response = step("Make request", ()->
                given(updateRequestSpec)

                .body(updateData)


                .when()
                .patch("/users/2")

                .then()
                .spec(updateResponseSpec)
                .extract().as(UpdateResponseModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", response.getName());
            assertEquals("zion resident", response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }
}
