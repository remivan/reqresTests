package specs;


import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static tests.TestBase.header;

public class BaseSpecs {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .header(header)
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers();


    public static ResponseSpecification responseSpecification(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .log(LogDetail.ALL)
                .build();
    }

}
