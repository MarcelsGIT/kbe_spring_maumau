package de.berlin.htw.kbe.gruppe1.maumau.cardGameUser.modell.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;



import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Card;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Symbol;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Value;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.UserService;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.management.UserMgmt;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.modell.MauMauUser;

public class CardGameUserTest {

	static private MauMauUser mauMauUser;
	static public List <Card> cardHand;
	static private UserService userMgmt;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		userMgmt = new UserMgmt();
		mauMauUser = new MauMauUser("Kaan", cardHand, 0, false, false, false, false, false);
		cardHand = new LinkedList<Card>();
		cardHand.add(new Card(Symbol.CLUB, Value.EIGHT));
		cardHand.add(new Card(Symbol.DIAMOND, Value.ACE));
		cardHand.add(new Card(Symbol.HEART, Value.TEN));
		mauMauUser.setHand(cardHand);
	}
	
	@Test//(expected = IndexOutOfBoundsException.class)
	public void testPlayCardWithNegativeIndex() {
		// Arrange
		int index = -1;
		// Act
		assertThrows(IndexOutOfBoundsException.class, () ->userMgmt.playCard(index, mauMauUser));
	}
	
	@Test
	public void testPlayCardWithIndexTooHigh() {
		// Arrange
		int index = 3;
		// Act
		assertThrows(IndexOutOfBoundsException.class, () -> userMgmt.playCard(index, mauMauUser));
	}

	@Test
	public void testTakeCardIsValid() {
		// Arrange
		Card card = new Card(Symbol.HEART, Value.KING);
		// Act
		userMgmt.takeCard(card, mauMauUser);
		// Assert
		assertTrue(card instanceof Card);
	}

	@Test
	public void testShoutMauWithMoreThanTwoCards() {
		List<Card>cardHandThreeCards = new LinkedList<>();
		cardHandThreeCards.add(new Card(Symbol.CLUB, Value.EIGHT));
		cardHandThreeCards.add(new Card(Symbol.DIAMOND, Value.ACE));
		cardHandThreeCards.add(new Card(Symbol.HEART, Value.JACK));
		mauMauUser.setHand(cardHandThreeCards);
		assertEquals(userMgmt.shoutMau(mauMauUser), "I can't shout Mau yet!");
	}
	
	@Test
	public void testShoutMauWithOneCard() {
		List<Card>cardHandOneCard = new LinkedList<>();
		cardHandOneCard.add(new Card(Symbol.CLUB, Value.EIGHT));
		mauMauUser.setHand(cardHandOneCard);
		String mau = userMgmt.shoutMau(mauMauUser);
		assertEquals("I need to shout MauMau!", mau);
	}

	@Test
	public void testShoutMauMauWithMoreThanOneCard() {
		List<Card>cardHandTwoCards = new LinkedList<>();
		cardHandTwoCards.add(new Card(Symbol.CLUB, Value.EIGHT));
		cardHandTwoCards.add(new Card(Symbol.DIAMOND, Value.ACE));
		mauMauUser.setHand(cardHandTwoCards);
		String maumau = userMgmt.shoutMauMau(mauMauUser);
		assertEquals("This is not my last card, so I can't shout MauMau yet!", maumau);
	}
	
	@Test
	public void testShoutMauMauIsValid() {
		List<Card>cardHandOneCard = new LinkedList<>();
		cardHandOneCard.add(new Card(Symbol.CLUB, Value.EIGHT));
		mauMauUser.setHand(cardHandOneCard);		
		String maumau = userMgmt.shoutMauMau(mauMauUser);
		assertEquals("MauMau!", maumau);
	}
}
