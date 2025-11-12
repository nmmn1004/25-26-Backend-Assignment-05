package com.gdg.blackjackapi.exception.game;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(Long id) {
        super("ID(" + id + ")는 존재하지 않는 게임입니다.");
    }
    public GameNotFoundException(String message) {
        super(message);
    }
}
