package com.gdg.blackjackapi.global.util;

import java.util.List;

public class CardUtil {
    /**
    * @return 1~10의 정수를 무작위로 반환
     * */
    public static int generateRandomCards() {
        return (int)(Math.random() * 10) + 1;
    }
    /**
     * @param cards - 정수 요소 2개를 가진 리스트
     * @return 리스트의 정수를 합계에 더하고 에이스를 센다. 에이스가 존재하고,
     * 에이스를 11로 취급해도 버스트를 하지 않는다면 합계에 10을 더하고 이 합계를 반환한다.
     * */
    public static int calculateHandCard(List<Integer> cards) {
        int sum = 0;
        int aceCount = 0;

        for (int card : cards) {
            if (card == 1) {
                aceCount++;
                sum += 1;
            } else {
                sum += card;
            }
        }

        while (aceCount > 0 && sum + 10 <= 21) {
            sum += 10;
            aceCount--;
        }
        return sum;
    }

}
