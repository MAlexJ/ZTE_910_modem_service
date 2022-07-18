package com.malexj.models.requests;

public record MessageRequest(String phoneNumber, String message) {
    @Override
    public String toString() {
        return "phone: " + this.phoneNumber + ", msg: " + this.message;
    }
}