package com.malexj.models.responses;

public record MessageResponse(String message) {

    @Override
    public String toString() {
        return "message: " + this.message;
    }
}