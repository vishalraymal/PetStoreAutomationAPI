package com.petapi.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.petapi.endpoints.EndPointsOperations;
import com.petapi.payload.User;
import com.petapi.utilities.DataProviders;

import io.restassured.response.Response;

public class DataDrivenTests {

	User userPayload;
	@Test(priority=1, dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPostUser(String userId, String username, String fname, String lname, String useremail, String pwd, String ph) {
		userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userId));
		userPayload.setUsername(username);
		userPayload.setFirstname(fname);
		userPayload.setLastname(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		Response response = EndPointsOperations.createUser(userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
	@Test(priority=2,dataProvider="usernames",dataProviderClass=DataProviders.class)
	public void testGetUserByName(String username) {
		Response response = EndPointsOperations.readUser(username);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3, dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testUpdateUser(String fname, String lname, String useremail) {
		//update data using payload
		userPayload.setFirstname(fname);
		userPayload.setLastname(lname);
		userPayload.setEmail(useremail);
		
		Response response = EndPointsOperations.updateUser(this.userPayload.getUsername(), userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after updation
		
		Response responseAfterUpdate = EndPointsOperations.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
	}
	
	@Test(priority=4,dataProvider="usernames",dataProviderClass=DataProviders.class)
	public void deleteUser(String username) {
		Response response = EndPointsOperations.deleteUser(username);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
	
	
	
}