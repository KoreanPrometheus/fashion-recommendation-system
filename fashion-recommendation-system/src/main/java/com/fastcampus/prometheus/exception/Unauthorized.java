package com.fastcampus.prometheus.exception;

public class Unauthorized extends CustomException {

    private static final String MESSAGE = "인증되지 않은 사용자입니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }

}
