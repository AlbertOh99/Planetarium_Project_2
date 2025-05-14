package com.revature.planetarium.controller;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;

import static org.junit.runners.Parameterized.*;

public class LoginAPI extends APIFixture{
    @Test
    public void testLogin(){
        HashMap data = new HashMap<>();
        data.put("username","Batman");
        data.put("password","Iamthenight1939");

        given().contentType("application/json").body(data).
                when().post("login").then().statusCode(200).log().all();




    }
    @Test
    public void testNegativeLogin(){  //expected status code 401 as per srs, we are getting 400
        HashMap data1 = new HashMap<>();
        data1.put("username","Batman");
        data1.put("password","Iamthenight19");
        given().contentType("application/json").body(data1).when().post("login").then().statusCode(401).log().all();


    }
    @Test
    public void testNegativeLogout(){  //expected status code 401 as per srs, we are getting 400 for logout functionality
        HashMap data1 = new HashMap<>();
        data1.put("username","Batman");
        data1.put("password","Iamthenight1939");
        given().contentType("application/json").body(data1).when().post("logout").then().statusCode(401).log().all();


    }
}
