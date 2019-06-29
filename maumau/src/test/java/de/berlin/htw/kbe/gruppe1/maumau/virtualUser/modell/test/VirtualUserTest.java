package de.berlin.htw.kbe.gruppe1.maumau.virtualUser.modell.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.berlin.htw.kbe.gruppe1.maumau.cardGame.modell.MauMau;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Card;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.CardDeck;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Symbol;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Value;
import de.berlin.htw.kbe.gruppe1.maumau.rules.modell.MauMauRules;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.modell.MauMauUser;
import de.berlin.htw.kbe.gruppe1.maumau.virtualUserAdministration.VirtualUserService;
import de.berlin.htw.kbe.gruppe1.maumau.virtualUserAdministration.management.VirtualUserMgmt;

public class VirtualUserTest {

	VirtualUserService virtualUserService;
	MauMau maumau;
	List <MauMauUser> players;
	
	@BeforeEach
	public void init() {
		virtualUserService = new VirtualUserMgmt();
		players = new LinkedList <MauMauUser>();
		players.add(new MauMauUser("Kaan", new LinkedList<Card>(), 0));
		players.add(new MauMauUser("Marcel", new LinkedList<Card>(), 0));
		players.add(new MauMauUser("Judith", new LinkedList<Card>(), 0));
		maumau = new MauMau(players, new MauMauRules(), new CardDeck(), new CardDeck(),
				0, false, null, 0, Symbol.HEART);
	}
	
	@Test
	public void testPlayNextPossibleCardFromHandSymbol() {
		Card lastCard = new Card(Symbol.CLUB, Value.ACE);
		LinkedList <Card> userCards = new LinkedList <Card>();
		userCards.add(new Card(Symbol.CLUB, Value.ACE));
		userCards.add(new Card(Symbol.CLUB, Value.EIGHT));
		MauMauUser virtualUser = new MauMauUser("username",userCards,0,true,false, false, false, true);
		Card card = virtualUserService.playNextPossibleCardFromHand(virtualUser, maumau, lastCard);
		assertEquals(card.getSymbol(), lastCard.getSymbol());	
		
	}
	
	@Test
	public void testPlayNextPossibleCardFromHandValue() {
		Card lastCard = new Card(Symbol.DIAMOND, Value.ACE);
		LinkedList <Card> userCards = new LinkedList <Card>();
		userCards.add(new Card(Symbol.CLUB, Value.ACE));
		userCards.add(new Card(Symbol.CLUB, Value.EIGHT));
		MauMauUser virtualUser = new MauMauUser("username",userCards,0,true,false, false, false, true);
		Card card = virtualUserService.playNextPossibleCardFromHand(virtualUser, maumau, lastCard);
		assertEquals(card.getValue(), lastCard.getValue());	
		
	}
	
	@Test
	public void testPlayNextPossibleCardFromHandUserWish() {
		Card lastCard = new Card(Symbol.CLUB, Value.JACK);
		LinkedList <Card> userCards = new LinkedList <Card>();
		userCards.add(new Card(Symbol.CLUB, Value.ACE));
		userCards.add(new Card(Symbol.HEART, Value.EIGHT));
		MauMauUser virtualUser = new MauMauUser("username",userCards,0,true,false, false, false, true);
		Card card = virtualUserService.playNextPossibleCardFromHand(virtualUser, maumau, lastCard);
		assertEquals(card.getSymbol(), Symbol.HEART);	
		
	}
	
	@Test
	public void testSetMauIfPossibleTrue() {
		LinkedList <Card> userCards = new LinkedList <Card>();
		userCards.add(new Card(Symbol.CLUB, Value.ACE));
		userCards.add(new Card(Symbol.HEART, Value.EIGHT));
		MauMauUser virtualUser = new MauMauUser("username",userCards,0,true,false, false, false, true);
		MauMauUser user = virtualUserService.setMauIfPossible(virtualUser, maumau);
		assertTrue(user.isMau());
		
	}
	
	@Test
	public void testSetMauIfPossibleFalse() {
		LinkedList <Card> userCards = new LinkedList <Card>();
		userCards.add(new Card(Symbol.CLUB, Value.ACE));
		userCards.add(new Card(Symbol.HEART, Value.EIGHT));
		userCards.add(new Card(Symbol.HEART, Value.NINE));
		MauMauUser virtualUser = new MauMauUser("username",userCards,0,true,false, false, false, true);
		MauMauUser user = virtualUserService.setMauIfPossible(virtualUser, maumau);
		assertFalse(user.isMau());
		
	}
	
	

	@Test
	public void testSetMauMauIfPossibleTrue() {
		LinkedList <Card> userCards = new LinkedList <Card>();
		userCards.add(new Card(Symbol.CLUB, Value.ACE));
		MauMauUser virtualUser = new MauMauUser("username",userCards,0,true,false, false, false, true);
		MauMauUser user = virtualUserService.setMauMauIfPossible(virtualUser, maumau);
		assertTrue(user.isMaumau());
	}
	
	
	
	@Test
	public void testSetMauMauIfPossibleFalse() {
		LinkedList <Card> userCards = new LinkedList <Card>();
		userCards.add(new Card(Symbol.CLUB, Value.ACE));
		userCards.add(new Card(Symbol.HEART, Value.EIGHT));
		userCards.add(new Card(Symbol.HEART, Value.NINE));
		MauMauUser virtualUser = new MauMauUser("username",userCards,0,true,false, false, false, true);
		MauMauUser user = virtualUserService.setMauIfPossible(virtualUser, maumau);
		assertFalse(user.isMaumau());
	}

}
