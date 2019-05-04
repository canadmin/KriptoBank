package com.example.canyard.model;

public class Card {

    private String cardOwner;
    private String balance;
    private String hesapkimligi;
    private String number;

    public Card() {
    }

    public Card(String cardOwner, String balance, String hesapkimligi, String number) {
        this.cardOwner = cardOwner;
        this.balance = balance;
        this.hesapkimligi = hesapkimligi;
        this.number = number;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getHesapkimligi() {
        return hesapkimligi;
    }

    public void setHesapkimligi(String hesapkimligi) {
        this.hesapkimligi = hesapkimligi;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
