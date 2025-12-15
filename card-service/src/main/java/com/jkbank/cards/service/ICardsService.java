package com.jkbank.cards.service;

public interface ICardsService {
    /**
     * Create a new card for the given mobile number.
     * @param mobileNumber the mobile number for which to create the card
     */
    void createCard(String mobileNumber);
}
