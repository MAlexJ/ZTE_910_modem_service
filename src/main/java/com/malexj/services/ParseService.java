package com.malexj.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malexj.exceptions.JsonParseException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ParseService {

    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
    }

    public <T> T jsonToClass(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            throw new JsonParseException("Can't parse class - " + valueType.getName(), e);
        }
    }

    public String encodeStringToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            String hexCode = Integer.toHexString(ch).toUpperCase();
            String hexCodeWithAllLeadingZeros = "0000" + hexCode;
            sb.append(hexCodeWithAllLeadingZeros.substring(hexCodeWithAllLeadingZeros.length() - 4));
        }
        return sb.toString();
    }
}