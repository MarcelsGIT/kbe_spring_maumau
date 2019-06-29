package de.berlin.htw.kbe.gruppe1.maumau.userAdministration.management;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Card;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.UserService;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.modell.MauMauUser;

@Component
public class UserMgmt implements UserService {

	public Card playCard(int index, MauMauUser mauMauUser) {

		// Check if index is greater than amount of cards - Check if index is maller
		// than 0:
		if (index > mauMauUser.getHand().size() - 1) {
			throw new IndexOutOfBoundsException("Index can not be greater than the cards in your hand!");
		} else if (index < 0) {
			throw new IndexOutOfBoundsException("Index can not be smaller than 0!");
		}

		Card selectedCard = mauMauUser.getCardInHand(index);
		selectedCard.setOwner(null);
		selectedCard.setDeck(null);
		return selectedCard;

	}

	// Takes card to hand
	public MauMauUser takeCard(Card cardFromDeck, MauMauUser mauMauUser) {

		if (cardFromDeck != null) {
			mauMauUser.addCardToHand(cardFromDeck);
		}
		return mauMauUser;
	}

	public String shoutMau(MauMauUser mauMauUser) {
		if (mauMauUser.getHand().size() == 2) {
			return new String("Mau!");
		} else if (mauMauUser.getHand().size() == 1) {
			return new String("I need to shout MauMau!");
		} else {
			return new String("I can't shout Mau yet!");
		}
	}

	public String shoutMauMau(MauMauUser mauMauUser) {
		if (mauMauUser.getHand().size() == 1) {
			return new String("MauMau!");
		} else {
			return "This is not my last card, so I can't shout MauMau yet!";
		}
	}

	public List<MauMauUser> createUsers(List<String> userNames) {
		List<MauMauUser> userList = new LinkedList<MauMauUser>();
		if (userNames.size() == 1) {
			userList.add(new MauMauUser(userNames.get(0), new LinkedList(), 0, true, false, false, false, false));
			userList.add(new MauMauUser("Mr. Robot", new LinkedList(), 0, true, false, false, false, true));

		} else {

			for (String username : userNames) {
				userList.add(new MauMauUser(username, new LinkedList(), 0, true, false, false, false, false));
			}
		}
		return userList;
	}
}
