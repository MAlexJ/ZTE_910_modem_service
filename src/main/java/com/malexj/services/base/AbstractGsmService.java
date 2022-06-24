package com.malexj.services.base;

import com.malexj.models.requests.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.*;

@RequiredArgsConstructor
public abstract class AbstractGsmService {

    /**
     * Request body params for login
     */
    private static final String TEST_QUERY_PARAM_KEY = "isTest";
    private static final String TEST_QUERY_PARAM_VALUE = "false";

    private static final String FORM_QUERY_PARAM_KEY = "goformId";
    private static final String FORM_QUERY_PARAM_VALUE = "LOGIN";

    private static final String PWD_QUERY_PARAM_KEY = "password";

    @Value("${gsm.url.login}")
    private String loginUrl;

    @Value("${gsm.url.origin}")
    private String originUrl;

    @Value("${gsm.url.referer}")
    private String refererUrl;

    @Value("${gsm.pwd}")
    private String gsmPassword;

    private final RestTemplate restTemplate;


    public ResponseEntity<String> httpPost(HttpEntity<String> httpEntity) {
        return restTemplate.postForEntity(loginUrl, httpEntity, String.class);
    }

    protected String getHeadersCookies(ResponseEntity<String> responseEntity) {
        return Optional.ofNullable(responseEntity) //
                .map(HttpEntity::getHeaders) //
                .map(header -> header.getFirst(HttpHeaders.SET_COOKIE)) //
                .orElseThrow(() -> new IllegalArgumentException("Cookies not found in Login response"));
    }

    protected HttpEntity<String> buildRequestHttpEntity(String rawData, HttpHeaders headers) {
        return new HttpEntity<>(rawData, headers);
    }

    protected HttpHeaders buildHttpHeaders(String... cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONNECTION, "keep-alive");
        headers.add(CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        headers.add(ORIGIN, originUrl);
        headers.add(REFERER, refererUrl);
        if (Objects.nonNull(cookies) && cookies.length > 0) {
            Arrays.stream(cookies).forEach(val -> headers.add(COOKIE, val));
        }
        return headers;
    }

    protected UriComponentsBuilder baseUriComponentsBuilder() {
        return UriComponentsBuilder.newInstance() //
                .queryParam(TEST_QUERY_PARAM_KEY, TEST_QUERY_PARAM_VALUE);
    }

    protected UriComponents buildLoginUriComponents() {
        return baseUriComponentsBuilder() //
                .queryParam(FORM_QUERY_PARAM_KEY, FORM_QUERY_PARAM_VALUE) //
                .queryParam(PWD_QUERY_PARAM_KEY, gsmPassword) //
                .build();
    }

    protected UriComponents buildMessageUriComponents(MessageRequest messageRequest) {
        return baseUriComponentsBuilder() //
                .queryParam(FORM_QUERY_PARAM_KEY, "SEND_SMS") //
                .queryParam("notCallback", "true") //
                .queryParam("Number", messageRequest.phoneNumber()) //
                .queryParam("MessageBody", encodeStringToUnicode(messageRequest.message())) //
                .queryParam("ID", "1") //
                .queryParam("encode_type", "GSM7_default") //
                .build();
    }

    protected String buildRawData(UriComponents uriComponents) {
        return uriComponents//
                .toString() //
                .substring(1);
    }

    private String encodeStringToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            String hexCode = Integer.toHexString(ch).toUpperCase();
            String hexCodeWithAllLeadingZeros = "0000" + hexCode;
            sb.append(hexCodeWithAllLeadingZeros.substring(hexCodeWithAllLeadingZeros.length() - 4));
        }
        return sb.toString();
    }
}
