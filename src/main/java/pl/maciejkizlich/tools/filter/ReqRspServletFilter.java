package pl.maciejkizlich.tools.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ReqRspServletFilter implements Filter {

	private static final String ls = System.getProperty("line.separator");

	private static final Logger logger = (Logger) LoggerFactory.getLogger(ReqRspServletFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		BufferedRequestWrapper bufferedReqest = new BufferedRequestWrapper(httpServletRequest);
		BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

		final String headers = getRequestHeaders(httpServletRequest);

		final String requestBody = getRequestBody(bufferedReqest);

		final StringBuilder logMessage = new StringBuilder(ls) //
				.append("[sessionId = " + httpServletRequest.getSession().getId() + "]") //
				.append(" ") //
				.append("[HTTP Request] ") //
				.append(ls) //
				.append(httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI() + " " + httpServletRequest.getProtocol()) //
				.append(ls) //
				.append(headers) //
				.append("Content: " + requestBody) //
				.append(ls) //
				.append(ls); //

		chain.doFilter(bufferedReqest, bufferedResponse);

		final String responseBody = getResponseBody(bufferedResponse);

		final String responseHeaders = getResponseHeaders(bufferedResponse);

		logMessage.append("[HTTP Response] ") //
				.append(ls) //
				.append(responseHeaders) //
				.append("Response status: " + bufferedResponse.getStatus() + " " + HttpStatus.valueOf(bufferedResponse.getStatus()).getReasonPhrase()) //
				.append(ls) //
				.append("Response body: ") //
				.append(ls) //
				.append(responseBody)
				.append(ls); //

		logger.debug(logMessage.toString());

	}

	private String getResponseHeaders(BufferedResponseWrapper bufferedResponse) {
		Collection<String> responseHeadersNames = bufferedResponse.getHeaderNames();
		final StringBuilder responseHeaders = new StringBuilder();

		for (String header : responseHeadersNames) {
			responseHeaders.append(header) //
					.append(": ") //
					.append(bufferedResponse.getHeader(header)) //
					.append(ls); //
		}
		return responseHeaders.toString();
	}

	private String getResponseBody(BufferedResponseWrapper bufferedResponse) {
		String content = bufferedResponse.getContent();
		String contentType = bufferedResponse.getContentType();

		if (contentType != null) {

			if (contentType.contains(ContentType.APPLICATION_JSON_VALUE)) {
				return getJsonResponseBody(content);
			}

			if (contentType.contains(ContentType.APPLICATION_XML_VALUE)) {
				return getXmlResponseBody(content);
			}
		}
		
		return bufferedResponse.getContent().isEmpty() ? "---" : bufferedResponse.getContent();
	}

	private String getJsonResponseBody(String responseBody) {
		String prettyJsonString = "---";

		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(responseBody);
			prettyJsonString = gson.toJson(je);
		} catch (JsonSyntaxException ex) {
			throw new RuntimeException("Could not parse JSON response body", ex);
		}

		return prettyJsonString;
	}

	private String getXmlResponseBody(String content) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	private String getRequestBody(BufferedRequestWrapper bufferedReqest) throws IOException {
		String requestBody = bufferedReqest.getRequestBody();

		return requestBody.isEmpty() ? "---" : requestBody;
	}

	private String getRequestHeaders(HttpServletRequest httpServletRequest) {
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		final StringBuilder headers = new StringBuilder();

		while (headerNames.hasMoreElements()) {
			String header = (String) headerNames.nextElement();
			String value = httpServletRequest.getHeader(header);
			headers.append(header) //
					.append(": ") //
					.append(value) //
					.append(ls); //
		}
		return headers.toString();
	}

	@Override
	public void destroy() {
	}
}
