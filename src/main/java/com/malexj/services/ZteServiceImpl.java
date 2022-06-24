package com.malexj.services;


import com.malexj.models.requests.MessageRequest;
import com.malexj.models.responses.BatteryResponse;
import com.malexj.models.responses.LoginResponse;
import com.malexj.models.responses.MessageResponse;
import com.malexj.services.base.AbstractZteService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

@Service
public class ZteServiceImpl extends AbstractZteService {

    public ZteServiceImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public LoginResponse getLoginResponse() {
        String rawData = buildRawData(buildLoginUriComponents());
        HttpHeaders httpHeaders = buildHttpHeaders();
        HttpEntity<String> httpEntity = buildRequestHttpEntity(rawData, httpHeaders);
        ResponseEntity<String> response = httpPost(httpEntity);
        return new LoginResponse(response.getBody(), getHeadersCookies(response));
    }

    public MessageResponse sendMessage(MessageRequest messageRequest) {
        // 1. get cookies from http login response
        String cookies = getLoginResponse().cookies();
        // 2. add cookies to http request and send message with cookies
        ResponseEntity<String> messageResponse = sendMessage(messageRequest, cookies);
        return new MessageResponse(messageResponse.getBody());
    }

    public BatteryResponse getInfo(String requestParams) {
        HttpEntity<String> httpEntity = new HttpEntity<>(buildHttpHeaders());
        UriComponents uriComponents = buildInfoUriComponents(requestParams);
        ResponseEntity<String> info = httpGet(uriComponents, httpEntity);
        return jsonToClass(info.getBody(), BatteryResponse.class);
    }

    private ResponseEntity<String> sendMessage(MessageRequest request, String cookies) {
        UriComponents messageUriComponents = buildMessageUriComponents(request);
        String rawData = buildRawData(messageUriComponents);
        HttpHeaders httpHeaders = buildHttpHeaders(cookies);
        HttpEntity<String> httpEntity = buildRequestHttpEntity(rawData, httpHeaders);
        return httpPost(httpEntity);
    }
}