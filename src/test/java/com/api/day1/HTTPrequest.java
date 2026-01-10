package com.api.day1;

import org.testng.annotations.Test;
import com.api.utils.Env;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HTTPrequest {

    // Your own local API
    static final String BASE_URL = Env.get("BASE_URL");


    // ID is STRING because json-server may generate string IDs
    static String userId;

    // 🟢 CREATE USER
    @Test(priority = 1)
    void createUser() {

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "Shashi");
        data.put("role", "QA");

        var response =
                given()
                        .log().all()
                        .contentType("application/json")
                        .body(data)
                        .when()
                        .post(BASE_URL);

        response.then().statusCode(201);

        // Read ID as STRING
        userId = response.jsonPath().getString("id");
        System.out.println("Created User ID: " + userId);
    }

    // 🟡 UPDATE USER
    @Test(priority = 2, dependsOnMethods = "createUser")
    void updateUser() {

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "Senior Shashi");
        data.put("role", "Lead QA");

        given()
                .log().all()
                .contentType("application/json")
                .body(data)
                .when()
                .put(BASE_URL + "/" + userId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Senior Shashi"));
    }

    // 🔵 GET USER
    @Test(priority = 3, dependsOnMethods = "updateUser")
    void getUser() {

        given()
                .log().all()
                .when()
                .get(BASE_URL + "/" + userId)
                .then()
                .statusCode(200)
                .body("id", equalTo(userId));
    }

    // 🔴 DELETE USER
    @Test(priority = 4, dependsOnMethods = "getUser")
    void deleteUser() {

        given()
                .log().all()
                .when()
                .delete(BASE_URL + "/" + userId)
                .then()
                .statusCode(200);
    }
}
