package com.griddynamics.lidlbooking.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.griddynamics.lidlbooking.logging.LoggingUtils.parseBodySize;

@Component
public class RequestLoggingStrategyImpl implements RequestLoggingStrategy, InitializingBean {

    private final ObjectMapper objectMapper;
    @Value("${interceptor.headers}")
    private List<String> headers;
    @Value("${request.body_size}")
    private String bodySize;
    private int requestBodySize;

    @Autowired
    public RequestLoggingStrategyImpl(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String printRequest(final HttpServletRequest request) {
        try {
            return getId(request) + getUrl(request) + getHeaders(request) + getBody(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getId(final HttpServletRequest request) {
        return "Request: Id: " + request.getAttribute("id") + "\n";
    }

    private String getBody(final HttpServletRequest request) throws IOException {
        if (request.getContentLength() <= requestBodySize && request.getContentLength() > 0) {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            return "Body:\n" + body;
        }
        return "";
    }

    private String getUrl(final HttpServletRequest request) {
        return "Url: " + request.getMethod() + request.getRequestURI() + "\n";
    }

    private String getHeaders(final HttpServletRequest request) throws IOException {
        Map<String, String> headersMap = new HashMap<>();

        for (String s : headers) {
            headersMap.put(s, request.getHeader(s));
        }

        return "Headers:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(headersMap) + "\n";
    }

    @Override
    public void afterPropertiesSet() {
        requestBodySize = parseBodySize(bodySize);
    }
}
