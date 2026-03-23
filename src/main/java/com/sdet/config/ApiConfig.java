package com.sdet.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiConfig {
	
	public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
	
	public static RequestSpecification getRequestSpec() {
		return new RequestSpecBuilder()
						.setBaseUri(BASE_URL)
						.setContentType(ContentType.JSON)
						.build();
	}

}
