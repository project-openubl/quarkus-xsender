package io.github.project.openubl.quarkus.xsender.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class QuarkusXsenderResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/quarkus-xsender")
                .then()
                .statusCode(200)
                .body(is("ACEPTADO"));
    }
}
