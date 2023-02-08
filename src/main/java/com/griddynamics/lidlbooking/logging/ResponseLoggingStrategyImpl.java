package com.griddynamics.lidlbooking.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.griddynamics.lidlbooking.logging.LoggingUtils.parseBodySize;

@Component
public class ResponseLoggingStrategyImpl implements ResponseLoggingStrategy, InitializingBean {

    @Value("${interceptor.headers}")
    private List<String> headers;

    @Value("${response.headerName}")
    private String headerName;

    @Value("${response.body_size}")
    private String bodySize;

    private int responseBodySize;

    private final ObjectMapper objectMapper;

    @Autowired
    public ResponseLoggingStrategyImpl(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String printResponse(final HttpServletResponse response) {
        try {
            return getId(response) + getStatus(response) + getHeaders(response) + getBody(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getId(final HttpServletResponse response) {
        return "Response: Id: " + response.getHeader(headerName) + "\n";
    }

    private String getBody(final HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;

        if (responseWrapper.getContentSize() <= responseBodySize && responseWrapper.getContentSize() > 0) {
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.readTree(responseWrapper.getContentAsByteArray()).toPrettyString();
            return "Body:\n" + body;
        }
        return "";
    }

    private String getStatus(final HttpServletResponse response) {
        return "Status: " + response.getStatus() + "\n";
    }

    private String getHeaders(final HttpServletResponse response) throws JsonProcessingException {
        Map<String, String> headersMap = new HashMap<>();

        for (String s : headers) {
            headersMap.put(s, response.getHeader(s));
        }

        return "Headers:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(headersMap) + "\n";
    }

    @Override
    public void afterPropertiesSet() {
        responseBodySize = parseBodySize(bodySize);
    }
}
