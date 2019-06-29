package de.berlin.htw.kbe.gruppe1.maumau.cards.modell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="CardDecks")
@Component
public class CardDeck {

	@Id
	@GeneratedValue
	@Column(name="id", columnDefinition="INT")
	private Integer id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="cardDeckId", columnDefinition="INT")
	private List<Card> cards; // kein privates Feld
	
	public CardDeck() {
		super();
	}

	public CardDeck(List<Card> cards) {
		super();
		for(Card card : cards) {
			card.setOwner(null);
			card.setDeck(this);
		}
		this.cards = cards;
	}
	
	public CardDeck(List<Card> cards, String usage) {
		super();
		for(Card card : cards) {
			card.setOwner(null);
			card.setDeck(this);
		}
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}


	public void setCards(List<Card> cards) {
		for(Card card : cards) {
			card.setOwner(null);
			card.setDeck(this);
		}
		this.cards = cards;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<Object, Object> serialize(){
		Map<Object, Object> deckProps = new HashMap<Object, Object>();
		Map<Object, Object> cards = new HashMap<Object, Object>();
		int index = 0;
		for(Card card : this.cards) {
			cards.put(index, ( card != null ? card.serialize() : "" ));
			index++;
		}
		deckProps.put("id", this.id);
		deckProps.put("cards", cards);
		return deckProps;
	}
	
	public HashMap<Object, Object> deserialize(){
		
		return null;
	}
}
