package com.automationexercise.tests.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class APITests {

    @BeforeClass
    public void setup() {
        // Set the base URL for all API tests
        RestAssured.baseURI = "https://automationexercise.com/api";
    }

    @Test(description = "GET /productsList - Verify API returns a successful response")
    public void testGetAllProducts() {
        given()
            // No parameters or body needed for this GET request
        .when()
            .get("/productsList") // Send a GET request to the endpoint
        .then()
            .assertThat()
            // 1. Assert the HTTP status code is 200 (OK)
            .statusCode(200)
            // 2. Assert the response header has the correct content type
            .contentType(ContentType.JSON)
            // 3. Assert the response body contains a specific key ("products")
            .body("$", hasKey("products"))
            // 4. Assert a specific value in the JSON response (responseCode for this API)
            .body("responseCode", equalTo(200));
    }
}