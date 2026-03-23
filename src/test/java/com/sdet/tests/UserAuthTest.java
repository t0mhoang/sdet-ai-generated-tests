package com.sdet.tests;

import com.sdet.config.ApiConfig;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Epic("User Authentication")
@Feature("Login and Registration")
public class UserAuthTest {

    // ── USER DATA TESTS ──────────────────────────────────────────────

    @Test
    @Story("Fetch user")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /users returns 200 and list of users")
    void getAllUsers_returns200AndList() {
        Response response = given()
                .spec(ApiConfig.getRequestSpec())
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .extract().response();

        int userCount = response.jsonPath().getList("$").size();
        assertTrue(userCount > 0, "Should return at least one user");
    }

    @Test
    @Story("Fetch user")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /users/1 returns correct user data")
    void getExistingUser_returns200WithData() {
        Response response = given()
                .spec(ApiConfig.getRequestSpec())
            .when()
                .get("/users/1")
            .then()
                .statusCode(200)
                .extract().response();

        assertEquals(1, (int) response.jsonPath().get("id"));
        assertNotNull(response.jsonPath().getString("name"));
        assertNotNull(response.jsonPath().getString("email"));
    }

    @Test
    @Story("Fetch user")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /users/9999 returns 404 for non-existent user")
    void getNonExistentUser_returns404() {
        given()
            .spec(ApiConfig.getRequestSpec())
        .when()
            .get("/users/9999")
        .then()
            .statusCode(404);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @Story("Fetch user")
    @DisplayName("GET multiple valid users: all return 200")
    void getMultipleValidUsers_allReturn200(int userId) {
        given()
            .spec(ApiConfig.getRequestSpec())
        .when()
            .get("/users/" + userId)
        .then()
            .statusCode(200);
    }

    // ── POST TESTS ───────────────────────────────────────────────────

    @Test
    @Story("Create post")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("POST /posts with valid body returns 201")
    void createPost_validBody_returns201() {
        String body = """
                {
                    "title": "SDET AI Generated Test",
                    "body": "Testing AI-generated test suite",
                    "userId": 1
                }
                """;

        Response response = given()
                .spec(ApiConfig.getRequestSpec())
                .body(body)
            .when()
                .post("/posts")
            .then()
                .statusCode(201)
                .extract().response();

        assertEquals("SDET AI Generated Test",
                response.jsonPath().getString("title"));
        assertNotNull(response.jsonPath().get("id"));
    }

    @Test
    @Story("Create post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("POST /posts response time is under 3 seconds")
    void createPost_responseTimeUnder3Seconds() {
        String body = """
                {
                    "title": "Performance test",
                    "body": "Checking response time",
                    "userId": 1
                }
                """;

        given()
            .spec(ApiConfig.getRequestSpec())
            .body(body)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .time(org.hamcrest.Matchers.lessThan(3000L));
    }

    @Test
    @Story("Fetch post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /posts returns 200 and 100 posts")
    void getAllPosts_returns200AndCorrectCount() {
        Response response = given()
                .spec(ApiConfig.getRequestSpec())
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .extract().response();

        assertEquals(100, response.jsonPath().getList("$").size());
    }

    @Test
    @Story("Fetch post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /posts?userId=1 returns only posts for that user")
    void getPostsByUserId_returnsFilteredResults() {
        Response response = given()
                .spec(ApiConfig.getRequestSpec())
                .queryParam("userId", 1)
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .extract().response();

        response.jsonPath().getList("userId", Integer.class)
                .forEach(userId -> assertEquals(1, userId));
    }

    @Test
    @Story("Fetch post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /posts/999 returns 404 for non-existent post")
    void getNonExistentPost_returns404() {
        given()
            .spec(ApiConfig.getRequestSpec())
        .when()
            .get("/posts/999")
        .then()
            .statusCode(404);
    }
}