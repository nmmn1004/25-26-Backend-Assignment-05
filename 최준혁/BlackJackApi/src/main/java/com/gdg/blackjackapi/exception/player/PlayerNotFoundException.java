package com.gdg.blackjackapi.exception.player;

public class PlayerNotFoundException extends  RuntimeException{
    public PlayerNotFoundException(Long id) {
        super("ID(" + id + ")는 존재하지 않는 플레이어 입니다.");
    }
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
