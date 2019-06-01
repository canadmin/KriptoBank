package com.example.canyard.model;

public class User {
    private String cardOwner;

    public User(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public User() {
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }
}
