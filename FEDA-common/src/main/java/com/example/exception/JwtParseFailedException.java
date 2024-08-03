package com.example.exception;

public class JwtParseFailedException extends BaseException {

    public JwtParseFailedException() {
    }

    public JwtParseFailedException(String message) {
        super(message);
    }
}
