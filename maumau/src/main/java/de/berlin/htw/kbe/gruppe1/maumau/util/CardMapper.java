package de.berlin.htw.kbe.gruppe1.maumau.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Card;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Symbol;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Value;
import de.berlin.htw.kbe.gruppe1.maumau.util.exceptions.CardNotFoundException;

@Component
public class CardMapper {
	
	
	public static Card mapFrontendCardToBackendCard(String symbol, int value, List<Card> pile) {
		Symbol eSymbol = mapFrontendSymbol(symbol);
		Value eValue = mapFrontendValue(value);
		
		if(eSymbol == null || eValue == null) throw new CardNotFoundException();
		
		for(Card card : pile) 
			if(card.getSymbol() == eSymbol && card.getValue() == eValue) return card;
		
		//in case loop has not returned a card
		throw new CardNotFoundException();
	}
	
	public static Map<Object, Object> mapBackendCardToFrontendCard(Card card) {
		String symbol = mapBackendSymbol(card.getSymbol());
		int value = mapBackendValue(card.getValue());
		
		String shortName = symbol + value;
		
		//Object structur needs to be like: {shortName: {suit: x, rank: y}}
		Map<Object, Object> cardProps = new HashMap<Object, Object>(); // inner object
		Map<Object, Object> cardMap = new HashMap<Object, Object>();//outter object
		
		cardProps.put("suit", symbol);
		cardProps.put("rank", value);
		cardProps.put("shortName", shortName);
		cardMap.put("value", cardProps);
		
		return cardMap;
	}
	
	public static String mapBackendSymbol(Symbol symbol) {
		String fSymbol = "";
		if(symbol == Symbol.HEART) {
			fSymbol = "h";
		}else if(symbol == Symbol.CLUB) {
			fSymbol = "c";
		}else if(symbol == Symbol.DIAMOND) {
			fSymbol = "d";
		}else if(symbol == Symbol.SPADE) {
			fSymbol = "s";
		}
		
		return fSymbol;
	}
	
	public static Symbol mapFrontendSymbol(String symbol) {
		Symbol eSymbol = null;
		
		if(symbol.toLowerCase() == "h") {
			eSymbol = Symbol.HEART;
		}else if(symbol.toLowerCase() == "d") {
			eSymbol = Symbol.DIAMOND;
		}else if(symbol.toLowerCase() == "s") {
			eSymbol = Symbol.SPADE;
		}else if(symbol.toLowerCase() == "c") {
			eSymbol = Symbol.CLUB;
		}
		
		return eSymbol;
	}
	
	public static int mapBackendValue(Value value) {
		int fValue = 0;
		if(value == Value.ACE) {
			fValue = 1;
		}else if(value == Value.SEVEN) {
			fValue = 7;
		}else if(value == Value.EIGHT) {
			fValue = 8;
		}else if(value == Value.NINE) {
			fValue = 9;
		}else if(value == Value.TEN) {
			fValue = 10;
		}else if(value == Value.JACK) {
			fValue = 11;
		}else if(value == Value.QUEEN) {
			fValue = 12;
		}else if(value == Value.KING) {
			fValue = 13;
		}
		
		return fValue;
	}
	
	public static Value mapFrontendValue(int value) {
		Value eValue = null;
		if(value == 1) {
			eValue = Value.ACE;
		}else if(value == 7) {
			eValue = Value.SEVEN;
		}else if(value == 8) {
			eValue = Value.EIGHT;
		}else if(value == 9) {
			eValue = Value.NINE;
		}else if(value == 10) {
			eValue = Value.TEN;
		}else if(value == 11) {
			eValue = Value.JACK;
		}else if(value == 12) {
			eValue = Value.QUEEN;
		}else if(value == 13) {
			eValue = Value.KING;
		}else if(value == 14) {
			eValue = Value.ACE;//for the case ACE HIGH is true
		}
		
		return eValue;
	}
}
