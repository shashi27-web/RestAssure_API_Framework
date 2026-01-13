package com.api.day2;

/* Different ways to create post requests body
1) Post Request Body using Hashmap
2) Post Request Body creation using org.JSON
3) Post Request Body creation using POJO class
4) Post Request Body external json file data
 */

import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class diffWayToCreatePostRequest {

        // Declare id at class level
        static String id;

        // ✅ POST test
        @Test(priority = 1)
        void testPostusingHashMap() {
            HashMap<String, Object> data = new HashMap<>();
            data.put("name", "shashi");
            data.put("location", "Mohali");
            data.put("Phone", "123456");

            String courseArr[] = {"RestAssured", "python"};
            data.put("courses", courseArr);

            // Capture id from response
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
                    .path("id");   // ✅ capture id directly

            System.out.println("Created user with id: " + id);
        }

        // ✅ DELETE test
        @Test(priority = 2)
        void testDelete() {
            given()
                    .when()
                    .delete("http://localhost:3000/users/" + id)   // ✅ use captured id
                    .then()
                    .statusCode(200)
                    .log().all();

            System.out.println("Deleted user with id: " + id);
        }
}
