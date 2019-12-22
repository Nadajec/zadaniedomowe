package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Information;
import org.vistula.restassured.pet.Pet;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

public class PracaDomowaPut extends RestAssuredTest {


    @Test
    public void addPerson() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic((10));
        String myNationality = RandomStringUtils.randomAlphabetic(10);
        int mySalary = 1000;
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
    public void shouldSuccessfullyUpdateAll() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", 3);
        requestParams.put("name", "Jacek");
        requestParams.put("nationality", "Deutschland");
        requestParams.put("salary", 3000);

        Information information = given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/information/3")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("Jacek"))
                .body("nationality", equalTo("Deutschland"))
                .body("salary", equalTo(3000))
                .extract().body().as(Information.class);

        assertThat(information.getName()).isEqualTo("Jacek");
        assertThat(information.getSalary()).isEqualTo(3000);
        assertThat(information.getNationality()).isEqualTo("Deutschland");

    }

    @Test
    public void shouldReturn406WhenIdDoesNotExist() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);
        String myNationality = RandomStringUtils.randomAlphabetic(10);
        int mySalary = 3000;
        int myId = 345;

        requestParams.put("name", myName);
        requestParams.put("nationality", myNationality);
        requestParams.put("salary", mySalary);
        requestParams.put("id", myId);

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/information/" + myId)
                .then()
                .log().all()
                .statusCode(406)
                .body(equalTo("ID not found. Please use POST method to create new entries"));

    }

    @Test
    public void shouldDeletePerson() {
        given().delete("/information/3")
                .then()
                .log().all()
                .statusCode(204);
    }
}

