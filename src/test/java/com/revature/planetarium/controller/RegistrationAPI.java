package com.revature.planetarium.controller;

import io.restassured.http.ContentType;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.runners.Parameterized.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class RegistrationAPI extends APIFixture{
        @Test
        public void testRegistrationApi(){
            HashMap data = new HashMap<>();
            data.put("username","bobby");
            data.put("password","Bobb1");

            given().contentType("application/json").body(data)
                    .when().post("register")
                    .then().statusCode(201).log().all();

        }

        @Test
        public void testNegative(){

            HashMap data1 = new HashMap<>();
            data1.put("username","Batman");
            data1.put("password","Bobb1");

            given().contentType("application/json").body(data1)
                    .when().post("register")
                    .then().statusCode(400).log().all();

        }


}
