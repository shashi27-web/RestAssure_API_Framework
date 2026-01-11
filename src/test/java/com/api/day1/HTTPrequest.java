package com.api.day1;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HTTPrequest {

    static String userId;

    @BeforeClass
    void setup() {
        baseURI = "http://localhost:3000";  // ✅ set base URI once
    }

    // 🟢 CREATE USER
    @Test(priority = 1)
    void createUser() {
        var response =
                given()
                        .log().all()
                        .contentType("application/json")
                        .body(Map.of("name", "Shashi", "role", "QA"))
                        .when()
                        .post("/users");

        response.then()
                .statusCode(201)
                .body("name", equalTo("Shashi"))
                .body("role", equalTo("QA"));

        userId = response.jsonPath().getString("id");
        System.out.println("✅ Created User ID: " + userId);
    }

    // 🟡 UPDATE USER
    @Test(priority = 2, dependsOnMethods = "createUser")
    void updateUser() {
        given()
                .log().all()
                .contentType("application/json")
                .body(Map.of("name", "Senior Shashi", "role", "Lead QA"))
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Senior Shashi"))
                .body("role", equalTo("Lead QA"));
    }

    // 🔵 GET USER
    @Test(priority = 3, dependsOnMethods = "updateUser")
    void getUser() {
        given()
                .log().all()
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", equalTo("Senior Shashi"))
                .body("role", equalTo("Lead QA"));
    }

    // 🔴 DELETE USER
    @Test(priority = 4, dependsOnMethods = "getUser")
    void deleteUser() {
        given()
                .log().all()
                .when()
                .delete("/users/" + userId)
                .then()
                .statusCode(anyOf(is(200), is(204)));  // ✅ handle both cases
    }

    // ⚫ VERIFY USER IS DELETED
    @Test(priority = 5, dependsOnMethods = "deleteUser")
    void getUserAfterDelete() {
        given()
                .log().all()
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(404);  // ✅ json-server returns 404 after delete
    }
}