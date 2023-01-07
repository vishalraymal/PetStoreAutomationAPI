package com.petapi.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.petapi.endpoints.EndPointsOperations;
import com.petapi.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setUpData(){
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Logs
		logger = LogManager.getLogger(this.getClass());
	}

	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("********* Creating user ***************");
		Response response = EndPointsOperations.createUser(userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("********* Reading user information ***************");
		Response response = EndPointsOperations.readUser(this.userPayload.getUsername());
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3)
	public void testUpdateUser() {
		//update data using payload
		userPayload.setFirstname(faker.name().username());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		
		logger.info("********* Updating user information ***************");

		Response response = EndPointsOperations.updateUser(this.userPayload.getUsername(), userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after updation
		
		Response responseAfterUpdate = EndPointsOperations.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void deleteUser() {
		logger.info("********* Deleting user information ***************");

		Response response = EndPointsOperations.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
}
