package com.api.day1;

import com.api.base.BaseTest;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class CreateUserTest extends BaseTest {

    @Test
    public void createUser() {

        JSONObject body = new JSONObject();
        body.put("name", "Shashi");
        body.put("username", "shashi");
        body.put("email", "shashi@test.com");

        given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(body.toString())
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201);
    }
}
