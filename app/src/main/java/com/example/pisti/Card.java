package com.example.pisti;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Card {
    public CardShape cardShape;
    public CardNumber cardNumber;

    public Card() {

    }

    public Card(CardShape cardShape, CardNumber cardNumber) {
        this.cardShape = cardShape;
        this.cardNumber = cardNumber;
    }

    public static List<Card> createDeck() {
        List<Card> cardList = new ArrayList<>();//Oluşturduğumuz 0 deste

        for (CardShape shape : CardShape.values()) {
            for (CardNumber number : CardNumber.values()) {
                cardList.add(new Card(shape, number));
            }
        }
        return cardList;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static List<Card> shuffle() {
        List<Card> cardList = createDeck();
        List<Card> mixDeck = new ArrayList<>();//Karıştıracağımız yeni deste


        while (true) {
            Random random = new Random();
            int e = getRandomNumber(0, cardList.size());//0 ile liste büyüklüğü arası random sayı

            if (!mixDeck.contains(cardList.get(e))) { //eğer cardList içindeki sayı ile mixDeck içindeki eşleşmiyorsa ekle
                mixDeck.add(cardList.get(e));
            }
            if (mixDeck.size() == 52) {
                return mixDeck;
            }

        }


    }

    public static List<Card> dealer(List<Card> getAllCard) {
        List<Card> player = new ArrayList<>();

        int getAllCardSize = getAllCard.size();
        for (int i = 0; i < getAllCardSize; i++) {
            if (player.size() < 4) {
                player.add(getAllCard.get(0));
                getAllCard.remove(0);
            }
        }


        return player;
    }

    /*
    public static List<Card> dealer(List<Card> getAllCard) {
        List<Card> firstPlayer = new ArrayList<>();// 1. oyuncuya deste oluşturdum
        List<Card> secondPlayer = new ArrayList<>();// Bilgisayara deste oluştrudum


        for (int i = 0; i < getAllCard.size(); i++) {// 1. oyuncuya kart dağıttık(hep 0. indeks)
            if (firstPlayer.size() < 4) {
                firstPlayer.add(getAllCard.get(0));
                getAllCard.remove(0);
            } else if (secondPlayer.size() < 4) { // 2. oyuncuya kart dağıttık(hep 0. index)
                secondPlayer.add(getAllCard.get(0));
                getAllCard.remove(0);

            }
        }

        return cardList;
    }
*/

}




