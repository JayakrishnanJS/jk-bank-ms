package com.jkbank.cards.service.impl;

import com.jkbank.cards.constants.CardsConstants;
import com.jkbank.cards.dto.CardsDto;
import com.jkbank.cards.entity.Cards;
import com.jkbank.cards.exception.CardAlreadyExistsException;
import com.jkbank.cards.exception.ResourceNotFoundException;
import com.jkbank.cards.mapper.CardsMapper;
import com.jkbank.cards.repository.CardsRepository;
import com.jkbank.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;
    /**
     * Create a new card for the given mobile number.
     *
     * @param mobileNumber the mobile number for which to create the card
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already exists for mobile number: " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     * Fetch card details based on mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return the card data transfer object containing card details
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(String.valueOf(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
}
