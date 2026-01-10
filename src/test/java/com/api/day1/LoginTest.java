package com.api.day1;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    @Test
    public void loginSuccessful() {
        JSONObject data = new JSONObject();
        data.put("email", "eve.holt@reqres.in");
        data.put("password", "cityslicka");

        given()
                .contentType("application/json")
                .body(data.toString())
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .statusCode(200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .log().all();
    }

    @Test
    public void loginUnsuccessful() {
        JSONObject data = new JSONObject();
        data.put("email", "peter@klaven");

        given()
                .contentType("application/json")
                .body(data.toString())
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"))
                .log().all();
    }
}
