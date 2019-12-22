package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

public class InformationControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/information")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void addPerson() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic((10));
        String myNationality = RandomStringUtils.randomAlphabetic(10);
        int mySalary = 20000;
        requestParams.put("name", myName);
        requestParams.put("salary", mySalary);
        requestParams.put("nationality", myNationality);

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("nationality", equalTo(myNationality))
                .body("salary", equalTo(mySalary))
                .body("name", equalTo(myName))
                .body("id", greaterThan(0));
    }

    @Test
    public void shouldDeletePerson() {
        given().delete("/information/4")
                .then()
                .log().all()
                .statusCode(200);
    }
}
