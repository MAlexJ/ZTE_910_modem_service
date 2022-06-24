package com.malexj.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonParseException extends RuntimeException{
    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
