package com.api.day2;

/* Different ways to create post requests body
1) Post Request Body using Hashmap
2) Post Request Body creation using org.JSON
3) Post Request Body creation using POJO class
4) Post Request Body external json file data
 */

import org.testng.annotations.Test;
import java.util.HashMap;
import org.json.JSONObject;
import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class diffWayToCreatePostRequest {

    // ✅ Declare id at class level so all tests can reuse it
    static String id;

    // 1) Post Request Body using Hashmap
    @Test(priority = 1)
    void testPostUsingHashMap() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "shashi");
        data.put("location", "Mohali");
        data.put("Phone", "123456");

        String[] courseArr = {"RestAssured", "python"};
        data.put("courses", courseArr);

        id = given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("http://localhost:3000/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("shashi"))
                .body("location", equalTo("Mohali"))
                .body("Phone", equalTo("123456"))
                .body("courses", hasItems("RestAssured", "python"))
                .header("Content-Type", containsString("application/json"))
                .log().all()
                .extract()
                .path("id");

        System.out.println("Created user with id (HashMap): " + id);
    }

    @Test(priority = 2)
    void testDeleteHashMapUser() {
        given()
                .when()
                .delete("http://localhost:3000/users/" + id)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Deleted user with id (HashMap): " + id);
    }

    // 2) Post Request Body creation using org.JSON
    @Test(priority = 3)
    void testPostUsingJSONOrg() {
        JSONObject data = new JSONObject();
        data.put("name", "shashi");
        data.put("location", "Mohali");
        data.put("Phone", "123456");

        String[] courseArr = {"RestAssured", "python"};
        data.put("courses", courseArr);

        id = given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("http://localhost:3000/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("shashi"))
                .body("location", equalTo("Mohali"))
                .body("Phone", equalTo("123456"))
                .body("courses", hasItems("RestAssured", "python"))
                .header("Content-Type", containsString("application/json"))
                .log().all()
                .extract()
                .path("id");

        System.out.println("Created user with id (org.JSON): " + id);
    }

    @Test(priority = 4)
    void testDeleteJSONOrgUser() {
        given()
                .when()
                .delete("http://localhost:3000/users/" + id)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Deleted user with id (org.JSON): " + id);
    }

    // 3) Post Request Body creation using POJO
    @Test(priority = 5)
    void testPostUsingPOJO() {
        String[] courseArr = {"RestAssured", "python"};

        User user = new User("shashi", "Mohali", "123456", courseArr);

        id = given()
                .contentType("application/json")
                .body(user)
                .when()
                .post("http://localhost:3000/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("shashi"))
                .body("location", equalTo("Mohali"))
                .body("Phone", equalTo("123456"))
                .body("courses", hasItems("RestAssured", "python"))
                .header("Content-Type", containsString("application/json"))
                .log().all()
                .extract()
                .path("id");

        System.out.println("Created user with id (POJO): " + id);
    }

    @Test(priority = 6)
    void testDeletePOJOUser() {
        given()
                .when()
                .delete("http://localhost:3000/users/" + id)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Deleted user with id (POJO): " + id);
    }

    // 4) Post Request Body using external JSON file
    @Test(priority = 7)
    void testPostUsingExternalJSONFile() {

        File file = new File(System.getProperty("user.dir") + "/src/test/resources/user.json");
// ✅ path to your JSON file

        id = given()
                .contentType("application/json")
                .body(file)
                .when()
                .post("http://localhost:3000/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("shashi"))
                .body("location", equalTo("Mohali"))
                .body("Phone", equalTo("123456"))
                .body("courses", hasItems("RestAssured", "python"))
                .header("Content-Type", containsString("application/json"))
                .log().all()
                .extract()
                .path("id");

        System.out.println("Created user with id (External JSON File): " + id);
    }

    @Test(priority = 8)
    void testDeleteExternalJSONUser() {
        given()
                .when()
                .delete("http://localhost:3000/users/" + id)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Deleted user with id (External JSON File): " + id);
    }
}