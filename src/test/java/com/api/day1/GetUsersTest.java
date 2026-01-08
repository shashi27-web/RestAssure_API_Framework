package com.api.day1;

import com.api.base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GetUsersTest extends BaseTest {

    @Test
    public void getUsers() {

        given()
                .log().all()
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200);
    }
}
