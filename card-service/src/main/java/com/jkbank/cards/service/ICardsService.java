package com.jkbank.cards.service;

import com.jkbank.cards.dto.CardsDto;

public interface ICardsService {
    /**
     * Create a new card for the given mobile number.
     * @param mobileNumber the mobile number for which to create the card
     */
    void createCard(String mobileNumber);

    /**
     * Fetch card details based on mobile number.
     * @param mobileNumber the mobile number of the customer
     * @return the card data transfer object containing card details
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     * Update card details for the given card.
     * @param cardsDto the card data transfer object containing updated card details
     * @return true if the update was successful, false otherwise
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     * Delete card based on mobile number.
     * @param mobileNumber the mobile number of the customer
     * @return true if the deletion was successful, false otherwise
     */
    boolean deleteCard(String mobileNumber);
}
