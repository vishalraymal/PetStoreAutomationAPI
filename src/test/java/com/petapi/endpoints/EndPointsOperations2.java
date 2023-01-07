package com.petapi.endpoints;

import static io.restassured.RestAssured.*;

import java.util.ResourceBundle;

import com.petapi.payload.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

// Create, Read, Update, update , delete

public class EndPointsOperations2 {
	
	
	//Addition method created to get URL from properties files
	public static ResourceBundle getURLs() {
		
		ResourceBundle routes = ResourceBundle.getBundle("routes");
		return routes;
	}

	public static Response createUser(User payload) {
		
		Response res = given()
				       .contentType(ContentType.JSON)
				       .accept(ContentType.JSON)
				       .body(payload)
				     .when() 
				        .post(getURLs().getString("post_url"));

		return res;

	}
	
	public static Response readUser(String username) {
		Response res = given()
				        .pathParam("username", username)
				       
				     .when()
				        .get(getURLs().getString("get_url"));

		return res;

	}
	
	public static Response updateUser(String username, User payload) {
		Response res = given()
				       .contentType(ContentType.JSON)
				       .accept(ContentType.JSON)
				       .pathParam("username", username)
				       .body(payload)
				     .when()
				        .put(getURLs().getString("update_url"));

		return res;

	}
	
	public static Response deleteUser(String username) {
		Response res = given()
				        .pathParam("username", username)
				       
				     .when()
				        .delete(getURLs().getString("delete_url"));

		return res;

	}

}
