package org.gradle.support;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APIMRequests {

	private static final Logger LOG = LoggerFactory.getLogger(APIMRequests.class);

	public APIMResponse getRequest(String endpoint, String responsePayloadType, String token)
			throws ClientProtocolException, IOException {

		HttpGet httpGet = new HttpGet(endpoint);
		httpGet.addHeader(HttpHeaders.ACCEPT, responsePayloadType);
		httpGet.addHeader(HttpHeaders.AUTHORIZATION, token);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(httpGet);

		return new APIMResponse(response);
	}

	public APIMResponse postRequest(String endpoint, String responsePayloadType, String contentType, String token,
			String body) throws IOException {

		StringEntity postEntity = new StringEntity(body);

		HttpPost httpPost = new HttpPost(endpoint);
		httpPost.addHeader(HttpHeaders.ACCEPT, responsePayloadType);
		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, contentType);
		httpPost.addHeader(HttpHeaders.AUTHORIZATION, token);
		httpPost.setEntity(postEntity);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(httpPost);

		return new APIMResponse(response);
	}

	// public static void main(String args[]) throws ClientProtocolException,
	// IOException{
	//
	// HttpGet httpGet = new
	// HttpGet("https://jsonplaceholder.typicode.com/posts/1");
	// httpGet.addHeader(HttpHeaders.ACCEPT, "application/json");
	// httpGet.addHeader(HttpHeaders.AUTHORIZATION, "testAuth");
	//
	// StringEntity postEntity = new
	// StringEntity(ResourceFileLoader.readResourceContent("insert_one_post.json"));
	//
	// HttpPost httpPost = new
	// HttpPost("https://jsonplaceholder.typicode.com/posts");
	// httpPost.addHeader(HttpHeaders.ACCEPT, "application/json");
	// httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
	// httpPost.addHeader(HttpHeaders.AUTHORIZATION, "testAuth");
	// httpPost.setEntity(postEntity);
	//
	//
	// CloseableHttpClient httpclient = HttpClients.createDefault();
	// CloseableHttpResponse response = httpclient.execute(httpPost);
	//
	// APIManagerResponse clientRes = new APIManagerResponse(response);
	//
	// System.out.println("_+_+_+_+_+_+_+_+_ Response Code: " +
	// clientRes.getStatusCode());
	// System.out.println("_+_+_+_+_+_+_+_+_ response body: \n" +
	// clientRes.getEntityAsString());
	// }
}