package de.berlin.htw.kbe.gruppe1.maumau.cardGame.modell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.CardDeck;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Symbol;
import de.berlin.htw.kbe.gruppe1.maumau.rules.modell.MauMauRules;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.modell.MauMauUser;
import de.berlin.htw.kbe.gruppe1.maumau.util.CardMapper;

@Entity
@Table(name="Games")
@Component
public class MauMau {

	@Id
	@GeneratedValue
	@Column(name="id", columnDefinition="INT")
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cardDeckId")
	CardDeck deck;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="graveyardId")
	CardDeck graveyard;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="winnerId", columnDefinition="INT")
	MauMauUser winner = null;
	
	@Enumerated(EnumType.STRING)
	Symbol userwish;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="gameId")
	List<MauMauUser> players;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="rulesId")
	MauMauRules ruleSet;
	
	int currentPlayerIndex;
	boolean penaltyTime;
	boolean endGame;
	int amountSeven;
	boolean playAgain = false;
	
	public MauMau(){
		
	}
	
	public MauMau(List<MauMauUser> players, MauMauRules ruleSet, CardDeck deck, CardDeck graveyard,
			int currentPlayerIndex, boolean endGame, MauMauUser winner, int amountSeven, Symbol userwish) {
		super();
		this.players = players;
		this.ruleSet = ruleSet;
		this.deck = deck;
		this.graveyard = graveyard;
		this.currentPlayerIndex = currentPlayerIndex;
		this.endGame = endGame;
		this.winner = winner;
		this.amountSeven = amountSeven;
		this.userwish = userwish;
	}
	
	public boolean isPlayAgain() {
		return playAgain;
	}


	public void setPlayAgain(boolean playAgain) {
		this.playAgain = playAgain;
	}
	
	
	public Symbol getUserwish() {
		return userwish;
	}

	public void setUserwish(Symbol userwish) {
		this.userwish = userwish;
	}

	public int getAmountSeven() {
		return amountSeven;
	}
	public void setAmountSeven(int amountSeven) {
		this.amountSeven = amountSeven;
	}

	public List<MauMauUser> getPlayers() {
		return players;
	}
	public void setPlayers(List<MauMauUser> players) {
		this.players = players;
	}
	public MauMauRules getRuleSet() {
		return ruleSet;
	}
	public void setRuleSet(MauMauRules ruleSet) {
		this.ruleSet = ruleSet;
	}
	public CardDeck getDeck() {
		return deck;
	}
	public void setDeck(CardDeck deck) {
		this.deck = deck;
	}
	public CardDeck getGraveyard() {
		return graveyard;
	}
	public void setGraveyard(CardDeck graveyard) {
		this.graveyard = graveyard;
	}
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}
	public void setCurrentPlayerIndex(int currentPlayer) {
		this.currentPlayerIndex = currentPlayer;
	}

	public boolean isEndGame() {
		return endGame;
	}
	
	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	} 
	public MauMauUser getWinner() {
		return winner;
	}
	public void setWinner(MauMauUser winner) {
		this.winner = winner;
	}	
	public MauMauUser getCurrentPlayer() {
		return this.players.get(this.currentPlayerIndex);
	}
	public void setCurrentPlayer(MauMauUser player) {
		this.currentPlayerIndex = this.players.indexOf(player);
	}

	public boolean isPenaltyTime() {
		return penaltyTime;
	}

	public void setPenaltyTime(boolean penaltyTime) {
		this.penaltyTime = penaltyTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Map<Object, Object> serialize(){
		Map<Object, Object> maumau = new HashMap<Object, Object>();
		Map<Object, Object> users= new HashMap<Object, Object>();
		Map<Object, Object> decks = new HashMap<Object, Object>();
		Map<Object, Object> props = new HashMap<Object, Object>();
		int index = 0;
		for(MauMauUser user : this.players) {
			users.put(user != null ? user.getUsername() : "undefined" + index++, (user != null ? user.serialize() : ""));
		}
		
		decks.put("graveyard", (this.graveyard != null ? this.graveyard.serialize() : ""));
		decks.put("deck", (this.deck != null ? this.deck.serialize() : ""));
		
		props.put("winner", this.winner != null ? this.winner.serialize() : "");
		props.put("currentPlayer", this.getCurrentPlayer() != null ? this.getCurrentPlayer().serialize() : "");
		props.put("penaltyTime", this.penaltyTime != false ? this.penaltyTime : false);
		props.put("endGame", this.endGame != false ? this.endGame : false);
		props.put("amountSeven", this.amountSeven);
		props.put("playAgain", this.playAgain != false ? this.playAgain : false);
		props.put("userWish", CardMapper.mapBackendSymbol(this.userwish));
		props.put("rules", this.ruleSet != null ? this.ruleSet.serialize() : "");
		
		maumau.put("id", this.id);
		
		if(users.size() != 0) 
			maumau.put("users", users);
		
		if(decks.size() != 0)
		maumau.put("decks", decks);
		
		if(props.size() != 0)
		maumau.put("props", props);
		
		return maumau;
	}
}
