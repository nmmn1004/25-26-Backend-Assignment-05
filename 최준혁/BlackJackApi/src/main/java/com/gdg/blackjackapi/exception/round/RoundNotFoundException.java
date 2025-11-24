package com.gdg.blackjackapi.exception.round;

public class RoundNotFoundException extends RuntimeException {
    public RoundNotFoundException(String message) {
        super(message);
    }

    public RoundNotFoundException(Long id) {
        super("ID(" + id + ")는 존재하지 않는 라운드입니다.");
    }
}
