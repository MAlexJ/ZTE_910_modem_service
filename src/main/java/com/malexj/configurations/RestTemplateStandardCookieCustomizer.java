package com.malexj.configurations;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Resolve warming issue for cookies
 * link:
 * <a href="https://stackoverflow.com/questions/36473478/fixing-httpclient-warning-invalid-expires-attribute-using-fluent-api">Info</a>
 */
public class RestTemplateStandardCookieCustomizer implements RestTemplateCustomizer {

    @Override
    public void customize(final RestTemplate restTemplate) {
        final RequestConfig requestConfig = RequestConfig.custom() //
                .setCookieSpec(CookieSpecs.STANDARD) //
                .build();

        final HttpClient httpClient = HttpClients.custom() //
                .setDefaultRequestConfig(requestConfig) //
                .build();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}