package com.gdg.blackjackapi.domain.Round;

/**
 * 라운드의 승무패를 판별, Null 예외 오류 방지를 위한 PENDING 상태 추가
 * */
public enum RoundResult {
    WIN,
    DRAW,
    LOSE,
    PENDING
}
