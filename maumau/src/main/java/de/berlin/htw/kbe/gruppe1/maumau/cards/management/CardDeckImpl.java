package de.berlin.htw.kbe.gruppe1.maumau.cards.management;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.berlin.htw.kbe.gruppe1.maumau.cards.CardDeckService;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Card;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.CardDeck;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Symbol;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Value;

@Component
public class CardDeckImpl implements CardDeckService{

	public CardDeckImpl() {
		super();
	}

	public CardDeck saveCardToCardDeck(CardDeck cardDeck, Card... card) {
		List<Card> cardList = cardDeck.getCards();
		for (Card singleCard: card) {
			cardList.add(singleCard);
			singleCard.setDeck(cardDeck);
		}
		cardDeck.setCards(cardList);
		return cardDeck;
	}

	public List<Card> dealCards(CardDeck cardDeck, int amount, CardDeck graveyard) {
		if (cardDeck.getCards().size()< amount) {
			cardDeck = addCardsFromGraveyard(cardDeck, graveyard);
		}
		List<Card> cardDeckCards = cardDeck.getCards();
		List <Card> dealedCards = new LinkedList<Card>();
		for (int i=0; i<amount; i++) {
			dealedCards.add(cardDeckCards.get(i));
		}
		return dealedCards;
	}
	
	public List<Card> shuffle(CardDeck cardDeck) {
		List <Card> cardList = cardDeck.getCards();
		Collections.shuffle(cardList);
		return cardList;
	}

	public Card giveCard(CardDeck cardDeck, CardDeck graveyard) {
		if (cardDeck.getCards().size()<1) {
			cardDeck = addCardsFromGraveyard(cardDeck, graveyard);
			
		}
		Card card = cardDeck.getCards().get(0);
		return card;
	}

	public Card giveMostRecentCard(CardDeck cardDeck) {
		return cardDeck.getCards().get(cardDeck.getCards().size()-1);
	}
	
	public CardDeck addCardsFromGraveyard(CardDeck cardDeck, CardDeck graveyard) {
		List <Card> cardList = cardDeck.getCards();
		//Card lastPlayedCard = graveyard.getCards().get(graveyard.getCards().size() -1);
		for(Card card : graveyard.getCards()) {
			//if(card != lastPlayedCard) {
				card.setDeck(cardDeck);
				card.setOwner(null);
			//}
			//cardList.add(card);
		}
		//graveyard.getCards().remove(lastPlayedCard);
		cardList.addAll(graveyard.getCards());
		//graveyard.getCards().add(lastPlayedCard);
		cardDeck.setCards(cardList);
		return cardDeck;
	}

	public CardDeck removeCardsFromCardDeckList(CardDeck cardDeck, List<Card> cardsToBeRemoved) {
		List<Card> cardList = cardDeck.getCards();
		for(Card card : cardsToBeRemoved) {
			card.setOwner(null);
			card.setDeck(null);	
		}
		cardList.removeAll(cardsToBeRemoved);
		cardDeck.setCards(cardList);
		return cardDeck;
	}
	
	public List<Card> removeCardFromCardDeckList(List<Card> cards, Card cardToBeRemoved) {
		for (int i=0; i<cards.size(); i++) {
			if (cards.get(i).getSymbol() == cardToBeRemoved.getSymbol()&& cards.get(i).getValue()==cardToBeRemoved.getValue()) {
				cards.remove(i);
			}
		}
		return cards;
	}

	public List<Card> createCards() {
		List <Card> cards = new LinkedList <Card>();
		for (Symbol symbol : Symbol.values()) {
			for (Value value: Value.values()) {
				cards.add(new Card(symbol, value));
			}
		}
		return cards;
	}

	public CardDeck createCardDeck(List<Card> cardsList) {
		CardDeck newCardDeck = new CardDeck(cardsList);
		return newCardDeck;
	}
}
