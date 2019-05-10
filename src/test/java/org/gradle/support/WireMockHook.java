package org.gradle.support;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class WireMockHook {

	private int wireMockPort = 9110;
	private String wireMockHost = "localhost";
	private String bearerToken = "Bearer thisIsBearer";
	private WireMockServer wireMockServer;
	private WireMock wireMock;

	@Before("@wiremockApi")
	public void setupWiremockServer(){
		wireMockServer = new WireMockServer(wireMockPort);
		wireMock = new WireMock(wireMockHost, wireMockPort);
		wireMockServer.start();
		
		wireMock.register(WireMock.get(WireMock.urlPathMatching("/testGet")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json").withBody("{\"message\": \"Success\"}")));
		
		wireMock.register(WireMock.get(WireMock.urlPathMatching("/testGetLimitOffset"))
                .withQueryParam("limit", WireMock.matching("([0-9]*)"))
                .withQueryParam("offset", WireMock.matching("([0-9]*)"))
                .withHeader("Authorization", equalTo(bearerToken))
				.willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json").withBody("{\"message\": \"Limit offset success Success\"}")));
		
		wireMock.register(WireMock.post(WireMock.urlPathMatching("/testPost"))
				.withHeader("Authorization", equalTo(bearerToken))
				.withRequestBody(equalToJson("{\"message\": \"This is Body\"}"))				
				.willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json").withBody("{\"message\": \"Post Success\"}")));
	}
	
	@After("@wiremockApi")
	public void teardownWiremockServer() {
		wireMockServer.stop();
	}
}
