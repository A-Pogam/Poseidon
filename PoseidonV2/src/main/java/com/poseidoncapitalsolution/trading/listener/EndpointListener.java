package com.poseidoncapitalsolution.trading.listener;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor for logging HTTP requests and responses.
 * This class implements {@link HandlerInterceptor} to provide custom pre-handle and after-completion logic
 * for logging information about incoming HTTP requests and outgoing HTTP responses.
 */
public class EndpointListener implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(EndpointListener.class);

	/**
	 * Pre-handle method to log details about the incoming HTTP request.
	 * This method logs the HTTP method, URL, and any request parameters.
	 *
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @param handler the handler (or {@link HandlerInterceptor}) that is being called
	 * @return {@code true} to continue processing the request, {@code false} to halt the processing
	 * @throws Exception in case of any errors during request handling
	 */

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURL = java.net.URLDecoder.decode(request.getRequestURL().toString(), StandardCharsets.UTF_8);

		if (!request.getParameterMap().isEmpty()) {
			String requestParameters = "?" + request.getParameterMap().entrySet().stream().map(e -> e.getKey() + "=" + String.join(", ", e.getValue())).collect(Collectors.joining(" "));
			logger.info("URL requested: {} {}{}", request.getMethod(), requestURL, requestParameters);
		} else {
			logger.info("Endpoint requested: {} {}", request.getMethod(), requestURL);
		}

		return true;
	}

	/**
	 * After-completion method to log details about the outgoing HTTP response.
	 * This method logs the response status and additional details based on the status code.
	 *
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @param handler the handler (or {@link HandlerInterceptor}) that was executed
	 * @param exception any exception thrown on handler execution, if any
	 * @throws Exception in case of any errors during response handling
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
		int responseStatus = response.getStatus();

		switch (responseStatus) {
		case 200:
			logger.info("Response: Status {} OK", responseStatus);
			break;
		case 201:
			String responseLocation = java.net.URLDecoder.decode(response.getHeader("Location"), StandardCharsets.UTF_8);
			logger.info("Response: Status {} Created - Location : {}", responseStatus, responseLocation);
			break;
		case 204:
			logger.info("Response: Status {} No Content.", responseStatus);
			break;
		case 400:
			logger.error("Response: Status {} Bad Request.", responseStatus);
			break;
		case 404:
			logger.error("Response: Status {} Not Found.", responseStatus);
			break;
		case 500:
			logger.error("Response: Status {} Internal Server Error.", responseStatus);
			break;
		default:
			logger.error("Status unknown");
		}
	}
}