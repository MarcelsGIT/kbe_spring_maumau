package de.berlin.htw.kbe.gruppe1.maumau.userAdministration.modell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Card;

@Entity
@Table(name="Users")

@Component
public class MauMauUser extends CardGameUser {

	@Id
	@GeneratedValue
	@Column(name="id", columnDefinition="INT")
	private Integer id;
	
	public MauMauUser() {
		
	}

	public MauMauUser(String username, List<Card> hand, int wins,boolean isActive,boolean skipRound, boolean mau, boolean maumau, boolean virtualUser) {
		super(username, hand, wins);
		this.isActive = isActive;
		this.skipRound = skipRound;
		this.mau = mau;
		this.maumau = maumau;
		this.virtualUser = virtualUser;
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isVirtualUser() {
		return virtualUser;
	}

	public void setVirtualUser(boolean virtualUser) {
		this.virtualUser = virtualUser;
	}

	public MauMauUser(String username, List<Card> hand, int wins) {
		super(username, hand, wins);
	}
	
	private boolean isActive;
	private boolean skipRound;
	private boolean mau;
	private boolean maumau;
	private boolean virtualUser;
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isSkipRound() {
		return skipRound;
	}
	public void setSkipRound(boolean skipRound) {
		this.skipRound = skipRound;
	}
	public boolean isMau() {
		return mau;
	}
	public void setMau(boolean mau) {
		this.mau = mau;
	}
	public boolean isMaumau() {
		return maumau;
	}
	public void setMaumau(boolean maumau) {
		this.maumau = maumau;
	}
	
	
	public Map<Object, Object> serialize(){
		Map<Object, Object> userProps = new HashMap<Object, Object>();
		Map<Object, Object> hand = new HashMap<Object, Object>();
		
		int index = 0;
		
		for(Card card : this.hand) {
			hand.put(index++, (card != null ? card.serialize() : ""));
		}
		userProps.put("id", this.id);
		userProps.put("isActive", this.isActive);
		userProps.put("mau", this.mau);
		userProps.put("maumau", this.maumau);
		userProps.put("virtualUser", this.virtualUser);
		userProps.put("username", this.username);
		userProps.put("hand", hand);
		return userProps;
	}
}
