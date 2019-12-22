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

public class PracaDomowaPatch extends RestAssuredTest {


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
        public void shouldSuccessfullyUpdatePatch() {
            JSONObject requestParams = new JSONObject();
            String myName = RandomStringUtils.randomAlphabetic(10);
            String myNationality = RandomStringUtils.randomAlphabetic(10);
            int mySalary = 1000;

            requestParams.put("id", 3);
            requestParams.put("name", "Jacek");
            requestParams.put("nationality", "Deutschland");
            requestParams.put("salary", 1000);

            JSONObject requestParams2 = new JSONObject();
            String myName2 = RandomStringUtils.randomAlphabetic(10);
            String myNationality2 = RandomStringUtils.randomAlphabetic(10);
            int mySalary2 = 1000;

            requestParams.put("id", 3);
            requestParams2.put("name", myName2);
            requestParams2.put("nationality", myNationality2);
            requestParams2.put("salary", mySalary2);


            Information information = given().header("Content-Type", "application/json")
                    .body(requestParams2.toString())
                    .patch("/information/3")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("nationality", equalTo(myNationality2))
                    .body("name", equalTo(myName2))
                    .body("salary", equalTo(mySalary))
                    .extract().body().as(Information.class);

            assertThat(information.getName()).isEqualTo(myName2);
            assertThat(information.getSalary()).isEqualTo(mySalary);
            assertThat(information.getNationality()).isEqualTo(myNationality2);

        }

        @Test
        public void shouldDeletePerson() {
        given().delete("/information/3")
                .then()
                .log().all()
                .statusCode(204);
        }
    }

