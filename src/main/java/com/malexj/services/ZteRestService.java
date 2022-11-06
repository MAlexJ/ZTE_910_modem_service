package com.malexj.services;

import com.malexj.models.requests.MessageRequest;
import com.malexj.models.responses.BatteryResponse;
import com.malexj.models.responses.LoginResponse;
import com.malexj.models.responses.MessageResponse;
import com.malexj.services.base.AbstractRestService;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

@Log
@Service
public class ZteRestService extends AbstractRestService {

    public ZteRestService(RestTemplate restTemplate, ParseService parseService) {
        super(restTemplate, parseService);
    }

    /**
     * Login to ZTE modem
     *
     * @return response with cookies
     */
    public LoginResponse login() {
        String uriComponents = buildUriComponents(buildLoginUriComponents());
        log.info("build login URL - " + uriComponents);
        HttpHeaders httpHeaders = buildHttpHeaders();
        HttpEntity<String> httpEntity = buildRequestHttpEntity(uriComponents, httpHeaders);
        ResponseEntity<String> response = httpPost(httpEntity);
        log.info("login response: " + response.getBody());
        String cookies = extractCookies(response);
        return new LoginResponse(response.getBody(), cookies);
    }


    /**
     * Authorization based on header cookies
     *
     * @return cookies
     */
    public String getCookies(LoginResponse response) {
        return response.cookies();
    }

    /**
     * Send SMS message
     *
     * @param messageRequest contains phone number and message
     * @return result success or fault
     */
    public MessageResponse sendMessage(MessageRequest messageRequest) {
        // 1. login
        LoginResponse response = login();
        // 2. get cookies
        String cookies = getCookies(response);
        // 3. set cookies to send SMS request
        ResponseEntity<String> messageResponse = sendMessage(messageRequest, cookies);
        return new MessageResponse(messageResponse.getBody());
    }

    /**
     * Get info from ZTE modem
     */
    public BatteryResponse getInfo(String requestParams) {
        HttpEntity<String> httpEntity = new HttpEntity<>(buildHttpHeaders());
        UriComponents uriComponents = buildInfoUriComponents(requestParams);
        ResponseEntity<String> info = httpGet(uriComponents, httpEntity);
        return parseResponseEntity(info.getBody(), BatteryResponse.class);
    }

    private ResponseEntity<String> sendMessage(MessageRequest request, String cookies) {
        UriComponents messageUriComponents = buildMessageUriComponents(request);
        String fullUriComponents = buildUriComponents(messageUriComponents);
        HttpHeaders httpHeaders = buildHttpHeaders(cookies);
        HttpEntity<String> httpEntity = buildRequestHttpEntity(fullUriComponents, httpHeaders);
        return httpPost(httpEntity);
    }
}