package de.berlin.htw.kbe.gruppe1.maumau.virtualUserAdministration.management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.berlin.htw.kbe.gruppe1.maumau.cardGame.modell.MauMau;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Card;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Symbol;
import de.berlin.htw.kbe.gruppe1.maumau.rules.RulesService;
import de.berlin.htw.kbe.gruppe1.maumau.rules.management.RulesMgmt;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.modell.MauMauUser;
import de.berlin.htw.kbe.gruppe1.maumau.virtualUserAdministration.VirtualUserService;

@Component
public class VirtualUserMgmt implements VirtualUserService {

	@Autowired
	private RulesService ruleService;


	public Card playNextPossibleCardFromHand(MauMauUser virtualMauMauUser, MauMau mauMau, Card lastPlayedCard) {
		ensureServicesAvailability();
		List<Card> cards = virtualMauMauUser.getHand();
		Card validCard = null;
		for (Card card : cards) {
			if (ruleService.validCardOrNotValidCard(lastPlayedCard, card, mauMau.getUserwish(), mauMau.getRuleSet())) {
				validCard = card;
				break;
			}

		}

		return validCard;
	}


	public MauMauUser setMauIfPossible(MauMauUser virtualMauMauUser, MauMau mauMau) {
		ensureServicesAvailability();
		if (ruleService.checkShoutMauPossible(virtualMauMauUser, mauMau.getRuleSet())) {
			virtualMauMauUser.setMau(true);
		} else {
			virtualMauMauUser.setMau(false);
		}
		return virtualMauMauUser;
	}

	
	public MauMauUser setMauMauIfPossible(MauMauUser virtualMauMauUser, MauMau mauMau) {
		ensureServicesAvailability();
		if (ruleService.checkShoutMauMauPossible(virtualMauMauUser, mauMau.getRuleSet())) {
			virtualMauMauUser.setMaumau(true);
		} else {
			virtualMauMauUser.setMaumau(false);
		}
		return virtualMauMauUser;
	}
	
	
	
	public Symbol makeWhishByTakingFirstCardSymbol(MauMauUser virtualMauMauUser) {
		ensureServicesAvailability();
		Symbol symbol = virtualMauMauUser.getCardInHand(0).getSymbol();
		return symbol;
	}
	
	
	
	
	
	private void ensureServicesAvailability() {
		if(this.ruleService == null) this.ruleService = new RulesMgmt();
	}
}
