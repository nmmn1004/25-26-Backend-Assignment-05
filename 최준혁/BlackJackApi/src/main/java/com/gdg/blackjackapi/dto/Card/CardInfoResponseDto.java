package com.gdg.blackjackapi.dto.Card;

import com.gdg.blackjackapi.domain.Card.Card;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardInfoResponseDto {
    private Integer card1;
    private Integer card2;
    private String owner;

    public static CardInfoResponseDto from(Card card) {
        return CardInfoResponseDto.builder()
                .card1(card.getCard1())
                .card2(card.getCard2())
                .owner(card.getOwner().name())
                .build();
    }
}
