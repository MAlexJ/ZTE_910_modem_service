package com.malexj.controllers.base;

import com.malexj.exceptions.ValidationException;
import com.malexj.models.requests.MessageRequest;

import java.util.Objects;

public abstract class AbstractController {

    protected MessageRequest validateMessageRequest(MessageRequest request) {
        if (isNotNumeric(request.phoneNumber())) {
            throw new ValidationException("phone number must contain only numbers");
        }
        return request;
    }

    public boolean isNotNumeric(String value) {
        if (Objects.isNull(value)) {
            return true;
        }
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }
}
