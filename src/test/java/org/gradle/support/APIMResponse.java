package org.gradle.support;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class APIMResponse {

	private HttpResponse response;
	private String entity;
	private long contentLength = 0;

	public APIMResponse(HttpResponse response) throws IOException {

		HttpEntity responseEntity = response.getEntity();

		if (responseEntity != null) {
			this.entity = EntityUtils.toString(responseEntity);
			this.contentLength = responseEntity.getContentLength();
		}

		this.response = response;
	}

	public String getHeader(String header) {
		return response.getFirstHeader(header).getValue();
	}

	public Header[] getHeaders() {
		return response.getAllHeaders();
	}

	public int getStatusCode() {
		return response.getStatusLine().getStatusCode();
	}

	public String getEntityAsString() throws IOException {
		return entity;
	}

	public long getContentLength() {
		return contentLength;
	}
}
