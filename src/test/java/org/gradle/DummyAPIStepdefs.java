package org.gradle;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.gradle.support.APIMRequests;
import org.gradle.support.APIMResponse;
import org.gradle.support.ResourceFileLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class DummyAPIStepdefs {

	public String apiBaseUrl = "https://jsonplaceholder.typicode.com/posts";
	public String requestCounterContext = "/req-counter/v1";
	public String requestCounterResource = "/greeting";
	public String bearerToken = "477c3cde-6d4c-3d51-af00-29169ce7dd4c";
	public String apiHost;
	public String requestBody;
	public Properties cucumberTestProps;
	public APIMResponse apiManagerResponse;
	public APIMRequests apiManagerRequests = new APIMRequests();

	@Given("^I do a dummy step$")
	public void thisIsDummyStep() {
		System.out.println("_+_+_+_+_ Dummy Step _+_+_+_+__+");
	}

	@Given("^I call the posts context to get post \"(.*?)\"$")
	public void callGetApiWithPostId(String postId) throws ClientProtocolException, IOException {
		apiHost = apiBaseUrl + "/" + postId;
		apiManagerResponse = apiManagerRequests.getRequest(apiHost, "application/json", "testAuth");
	}

	@Given("^I call the posts context to get all posts$")
	public void callGetAllPosts() throws ClientProtocolException, IOException {
		apiManagerResponse = apiManagerRequests.getRequest(apiBaseUrl, "application/json", "testAuth");
	}

	@Given("^I call the posts context to POST \"(.*?)\" to posts list$")
	public void callPostRequestForPosts(String payload) throws IOException {
		requestBody = ResourceFileLoader.readResourceContent(payload);
		apiManagerResponse = apiManagerRequests.postRequest(apiBaseUrl, "application/json", "application/json",
				"testAuth", requestBody);
	}
	
	@Given("^I call the wiremock GET for \"(.*?)\" context$")
	public void callWiremockGetMethod(String context) throws IOException{
		apiManagerResponse = apiManagerRequests.getRequest("http://localhost:9110" + context, "", "");
	}
	
	@Given("^I call the wiremock GET for \"(.*?)\" context with \"(.*?)\" token$")
	public void callWiremockGetWithToken(String context, String token) throws IOException{
		apiManagerResponse = apiManagerRequests.getRequest("http://localhost:9110" + context + "?limit=3&offset=5", "", token);
	}
	
	@Given("^I call the wiremock POST for \"(.*?)\" context with body \"(.*?)\" with \"(.*?)\" token$")
	public void callWiremockPostWithToken(String context, String body, String token) throws IOException{
		String messageBody = ResourceFileLoader.readResourceContent(body);
		apiManagerResponse = apiManagerRequests.postRequest("http://localhost:9110" + context, "application/json", "application/json", token, messageBody);
	}

	@Then("^I will see a dummy result$")
	public void thisIsDummyResult() {
		System.out.println("_+_+_+_+_ Dummy result _+_+_+_+__+");
	}

	@Then("^the response code is (\\d+)$")
	public void verifyResponseCode(Integer statusCode) {
		assertThat(apiManagerResponse.getStatusCode()).isEqualTo(statusCode);
	}

	@Then("^the response should return (\\d+) posts$")
	public void verifyObjectCountInResponse(Integer numberOfobjects) throws ParseException, IOException {
		assertThat(convertResponseToJsonArray(apiManagerResponse).size()).isEqualTo(numberOfobjects);
	}

	@SuppressWarnings("unchecked")
	@Then("^the response body should match with \"(.*?)\" file$")
	public void isResponMatchingWithInput(String payload) throws ParseException, IOException {
		assertThat(convertResponseToJsonObject(apiManagerResponse.getEntityAsString()))
				.isEqualTo(convertResponseToJsonObject(ResourceFileLoader.readResourceContent(payload)));
	}

	@And("^the response should return user id \"(.*?)\"$")
	public void verifyResponseUserId(String userId) throws ParseException, IOException {
		assertThat(convertResponseToJsonObject(apiManagerResponse.getEntityAsString()).get("userId").toString())
				.isEqualTo(userId.toString());
	}

	private JSONObject convertResponseToJsonObject(String apiManagerResponse) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		JSONObject responseJson = (JSONObject) parser.parse(apiManagerResponse);
		return responseJson;
	}

	private JSONArray convertResponseToJsonArray(APIMResponse apiManagerResponse) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		JSONArray responseJsonArray = (JSONArray) parser.parse(apiManagerResponse.getEntityAsString());
		return responseJsonArray;
	}
}
