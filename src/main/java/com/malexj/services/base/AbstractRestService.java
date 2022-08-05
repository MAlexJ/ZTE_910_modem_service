package com.malexj.services.base;

import com.malexj.models.requests.MessageRequest;
import com.malexj.services.ParseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.*;

@Log
@RequiredArgsConstructor
public abstract class AbstractRestService {

    /**
     * Request params/values for http
     */
    private static final String TEST_QUERY_PARAM_KEY = "isTest";
    private static final String TEST_QUERY_PARAM_VALUE = "false";

    private static final String FORM_QUERY_PARAM_KEY = "goformId";
    private static final String FORM_QUERY_LOGIN_VALUE = "LOGIN";
    public static final String FORM_QUERY_SEND_SMS_VALUE = "SEND_SMS";

    private static final String PWD_QUERY_PARAM_KEY = "password";
    public static final String MULTI_DATA_PARAM_KEY = "multi_data";
    public static final String CMD_PARAM_KEY = "cmd";
    public static final String NOT_CALLBACK_PARAM_KEY = "notCallback";
    public static final String NUMBER_PARAM_KEY = "Number";
    public static final String MESSAGE_BODY_PARAM_KEY = "MessageBody";
    public static final String ID_PARAM_KEY = "ID";
    public static final String ENCODE_TYPE_PARAM_KEY = "encode_type";

    @Value("${gsm.url.post}")
    private String postUrl;

    @Value("${gsm.url.get}")
    private String getUrl;

    @Value("${gsm.url.origin}")
    private String originUrl;

    @Value("${gsm.url.referer}")
    private String refererUrl;

    @Value("${gsm.pwd}")
    private String gsmPassword;

    private final RestTemplate restTemplate;

    private final ParseService parseService;


    protected ResponseEntity<String> httpPost(HttpEntity<String> httpEntity) {
        return restTemplate.postForEntity(postUrl, httpEntity, String.class);
    }

    protected ResponseEntity<String> httpGet(UriComponents fullUri, HttpEntity<String> httpEntity) {
        return restTemplate.exchange(fullUri.toUriString(), HttpMethod.GET, httpEntity, String.class);
    }

    /**
     * Extract headers cookies from Http login response
     */
    protected String extractCookies(ResponseEntity<String> responseEntity) {
        HttpHeaders httpHeaders = Optional.ofNullable(responseEntity) //
                .map(HttpEntity::getHeaders) //
                .orElseThrow(() -> new IllegalArgumentException("Headers not found in login response"));
        log.info("login headers: " + httpHeaders);
        return Optional.ofNullable(httpHeaders) //
                .map(header -> header.getFirst(HttpHeaders.SET_COOKIE)) //
                .orElseThrow(() -> new IllegalArgumentException("Cookies not found in login response"));
    }

    protected <T> T parseResponseEntity(String body, Class<T> valueType) {
        return parseService.jsonToClass(body, valueType);
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

    protected UriComponents buildInfoUriComponents(String requestParams) {
        return UriComponentsBuilder.fromUriString(getUrl) //
                .queryParam(TEST_QUERY_PARAM_KEY, TEST_QUERY_PARAM_VALUE) //
                .queryParam(MULTI_DATA_PARAM_KEY, "1") //
                .queryParam(CMD_PARAM_KEY, requestParams) //
                .build();
    }

    protected UriComponents buildLoginUriComponents() {
        return baseUriComponentsBuilder() //
                .queryParam(FORM_QUERY_PARAM_KEY, FORM_QUERY_LOGIN_VALUE) //
                .queryParam(PWD_QUERY_PARAM_KEY, gsmPassword) //
                .build();
    }

    protected UriComponents buildMessageUriComponents(MessageRequest messageRequest) {
        String unicodeMsg = parseService.encodeStringToUnicode(messageRequest.message());
        return baseUriComponentsBuilder() //
                .queryParam(FORM_QUERY_PARAM_KEY, FORM_QUERY_SEND_SMS_VALUE) //
                .queryParam(NOT_CALLBACK_PARAM_KEY, "true") //
                .queryParam(NUMBER_PARAM_KEY, messageRequest.phoneNumber()) //
                .queryParam(MESSAGE_BODY_PARAM_KEY, unicodeMsg) //
                .queryParam(ID_PARAM_KEY, "1") //
                .queryParam(ENCODE_TYPE_PARAM_KEY, "GSM7_default") //
                .build();
    }

    /**
     * Build URL from constituent components (url, path parameters)
     */
    protected String buildUriComponents(UriComponents uriComponents) {
        return uriComponents//
                .toString() //
                .substring(1);
    }

}