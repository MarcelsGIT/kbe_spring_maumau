package de.berlin.htw.kbe.gruppe1.maumau.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.berlin.htw.kbe.gruppe1.maumau.cardGame.MauMauService;
import de.berlin.htw.kbe.gruppe1.maumau.cardGame.modell.MauMau;
import de.berlin.htw.kbe.gruppe1.maumau.cards.CardDeckService;
import de.berlin.htw.kbe.gruppe1.maumau.cards.modell.Card;
import de.berlin.htw.kbe.gruppe1.maumau.persistence.PersistenceService;
import de.berlin.htw.kbe.gruppe1.maumau.persistence.modell.JPAHandler;
import de.berlin.htw.kbe.gruppe1.maumau.rules.RulesService;
import de.berlin.htw.kbe.gruppe1.maumau.rules.modell.MauMauRules;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.UserService;
import de.berlin.htw.kbe.gruppe1.maumau.userAdministration.modell.MauMauUser;
import de.berlin.htw.kbe.gruppe1.maumau.util.CardMapper;
import de.berlin.htw.kbe.gruppe1.maumau.util.exceptions.CardNotFoundException;
import de.berlin.htw.kbe.gruppe1.maumau.virtualUserAdministration.VirtualUserService;

@Controller
public class GameController {

	@Value("${spring.application.name}")
	String appName;

	@Autowired
	private MauMauService mauMauService;
	@Autowired
	private RulesService mauMauRules;

	@Autowired
	private MauMau maumau;

	@Autowired
	private CardDeckService cardDeckService;

	@Autowired
	private UserService userService;

	@Autowired
	private RulesService rulesService;

	@Autowired
	private MauMauRules rules;

	private MauMauUser lastPlayer;

	@Autowired
	private JPAHandler handler;

	@Autowired
	private PersistenceService persistenceService;

	@Autowired
	private VirtualUserService virtualUserService;

	private boolean persistGames;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("appName", this.appName);
		return "home";
	}

	@GetMapping("/gameConfig")
	public String gameConfig(Model model) {

		return "configureGame";
	}

	@GetMapping("/game")
	public String game(@RequestParam List<String> userNames, @RequestParam int newGame, @RequestParam int virtualPlayer,
			Model model) {
		this.maumau = this.mauMauService.handleGameStart(userNames, this.rules, 5);
		return "maumau";
	}

	/*
	 * Request during the game are always POST and return a JSON Object
	 */

	/*
	 * Please write into the comments which solution you chose. Please describe in
	 * short the structure of the response object. e.g. data = card.serialized OR
	 * data = {card: {rank: ##, suit: ".."}}
	 */

	/*
	 * No Parameters needed. Response is just a serialized MauMau Object.
	 */
	@PostMapping(path = "/setupGame", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<Object, Object> setupGame() {
		return this.maumau != null ? this.maumau.serialize() : new HashMap<Object, Object>();
	}

	/*
	 * param: symbol, value || will look like this: {rank: value, suit: symbol}
	 * response: hasToTake (bool), hasToPlay (serialized Symbol (restriction when a
	 * jack was played)), amountToTake (int), jack (bool, when this player can
	 * choose the color), nextPlayer ({username: username})
	 * 
	 * possible approaches: 1. Add a property to maumau which holds the playedCard
	 * and add it to the serialize Method under props 2. Add the object manually
	 * within this method 3. Only send the serialized card (not recommended) as
	 * card.serialize()
	 * 
	 * Check before allowing to play: 	1. User correct user 
	 * 									2. If last card bube, check if user wish fulfilled 
	 * 									3. If last card 7 check if maumau.getAmountSeven>0, if yes, card has to be a 7
	 * 									4. Check if card is generally allowed to be played
	 * 
	 */
	@PostMapping(path = "/playCard", consumes = "application/json", produces = "application/json")
	public Map<Object, Object> playCard(@RequestParam Map<Object, Object> played) {

		try {

			// Deserialize the Card Object, if the player who made the request is not the
			// current Player an exception is thrown
			Card playedCard = CardMapper.mapFrontendCardToBackendCard((String) played.get("suit"),
					(int) played.get("rank"), this.maumau.getCurrentPlayer().getHand());
		} catch (CardNotFoundException e) {
			// maybe return an error code 403 forbidden? dont know how...
		}
		return null;
	}

	/*
	 * param: We don't need any parameters here as we're developing a local game at
	 * the moment. In a local game we can never know which user has presses the
	 * button due to the fact they are using the same mouse The Solution: the
	 * current player gets a card. No matter who is pressing the button. response:
	 * the taken card AND the userName of the user that gets the card. the three
	 * approaches mentioned @takeCard are possible here
	 * 
	 * Check beofre giving card: 1. If maumau.getAmountSeven>0; Give amount of cards
	 * that the user has to take - not only the single card
	 * E.g.:	amount 7 = 1 -> User has to take two cards
	 * 			amount 7 = 2 -> User has to take four cards
	 * Don't forget to set amountSeven to 0 afterwards
	 * 
	 */
	@PostMapping(path = "/takeCard", consumes = "application/json", produces = "application/json")
	public Map<Object, Object> takeCard() {
		return null;
	}

	/*
	 * param @See @takeCard response: a boolean telling if it was right to shout
	 * mau. Question: What should happen if a player shouts mau even if he is not
	 * allowed to? 1. Nothing 2. deal a penalty card 3. Give the user the
	 * information that he/she does currently not have the permision to do that
	 */
	@PostMapping(path = "/shoutMau", consumes = "application/json", produces = "application/json")
	public Map<Object, Object> shoutMau() {
		return null;
	}

	/*
	 * param @See @takeCard response: same as @shoutMau but for maumau
	 */
	@PostMapping(path = "/shoutMauMau", consumes = "application/json", produces = "application/json")
	public Map<Object, Object> shoutMauMau() {
		return null;
	}
	
	
	////////////Method for making user wish missing////////
	
	///////////Method for informing other user about userwish missing/////////
	
	/////////Method for skipRound missing (just set next player if playCard checks if person who wants to play is currentPlayer////////
	
	////////All virtual user methods missing (play next possible  card, set userwish, shout mau, shout maumau)//////
	
	

	
	
	
	
	
	
	
	

	/*
	 * Deprecated Stuff nobody needs.....
	 */
	/*
	 * @PostMapping(path = "/instanciateUsers", consumes = "application/json",
	 * produces = "application/json") public Map<String,String> shoutMau(){ return
	 * null; }
	 * 
	 * @PostMapping(path = "/instanciateUsers", consumes = "application/json",
	 * produces = "application/json") public Map<String,String> shoutMauMau(){
	 * return null; }
	 * 
	 * @PostMapping(path = "/instanciateUsers", consumes = "application/json",
	 * produces = "application/json") public Map<String,String> playCard(){ return
	 * null; }
	 * 
	 * @PostMapping(path = "/instanciateUsers", consumes = "application/json",
	 * produces = "application/json") public Map<String,String> takeCard(){ return
	 * null; }
	 */

	/*
	 * @PostMapping(path = "/instanciateUsers", consumes = "application/json",
	 * produces = "application/json") public Map<String, String>
	 * createUsers(@RequestParam List<String> userNames){ //this.maumau =
	 * mauMauService.handleGameStart(userNames, rules, 5);
	 * this.maumau.setPlayers(this.userService.createUsers(userNames)); Map<String,
	 * String> response = new HashMap<String, String>(); response.put("success", ""
	 * + userNames.size() + " created."); return response; }
	 */
	/*
	 * @PostMapping(path = "/test", consumes = "application/json", produces =
	 * "application/json")
	 * 
	 * @ResponseBody public Map<String, String>
	 * easyRespond(@RequestParam("firstname") String
	 * firstname, @RequestParam("lastname") String lastname) { Map<String, String>
	 * response = new HashMap<String, String>(); response.put("responseFirstname",
	 * firstname); response.put("responseLastname", lastname); return response; }
	 */
}
