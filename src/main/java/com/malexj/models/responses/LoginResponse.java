package com.malexj.models.responses;

public record LoginResponse(String message, String cookies) {
    @Override
    public String toString() {
        return "message: " + this.message + ", cookies: " + this.cookies;
    }
}