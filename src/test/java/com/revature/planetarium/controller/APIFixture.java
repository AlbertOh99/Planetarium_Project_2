package com.revature.planetarium.controller;

import com.revature.planetarium.util.TestUtilities;
import io.restassured.RestAssured;
import org.junit.Before;

import java.io.IOException;

import com.revature.planetarium.util.TestUtilities;
import io.restassured.RestAssured;
import org.junit.Before;

import java.io.IOException;

public class APIFixture {
    @Before
    public void setupRestAssured(){
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Before
    public void resetDatabase() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
    }
}
