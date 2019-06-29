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
	public String game(@RequestParam List<String> userNames, @RequestParam int newGame, @RequestParam int virtualPlayer, Model model) {
		this.maumau = this.mauMauService.handleGameStart(userNames, this.rules, 5);
		return "maumau";
	}
	
	/*
	 * Request during the game are always POST and return a JSON Object 
	 * */
	@PostMapping(path = "/setupGame", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<Object, Object> setupGame(){
		return this.maumau != null ? this.maumau.serialize() : new HashMap<Object,Object>();
	}
	
	@PostMapping(path = "/instanciateUsers", consumes = "application/json", produces = "application/json")
	public Map<Object, Object> dealCards(){
		Map<Object, Object> response = new HashMap<Object, Object>();
		return response;
	}
	
	/*@PostMapping(path = "/instanciateUsers", consumes = "application/json", produces = "application/json")
	public Map<String,String> shoutMau(){
		return null;
	}
	
	@PostMapping(path = "/instanciateUsers", consumes = "application/json", produces = "application/json")
	public Map<String,String> shoutMauMau(){
		return null;
	}
	
	@PostMapping(path = "/instanciateUsers", consumes = "application/json", produces = "application/json")
	public Map<String,String> playCard(){
		return null;
	}
	
	@PostMapping(path = "/instanciateUsers", consumes = "application/json", produces = "application/json")
	public Map<String,String> takeCard(){
		return null;
	}*/
		
	
	/*@PostMapping(path = "/instanciateUsers", consumes = "application/json", produces = "application/json")
	public Map<String, String> createUsers(@RequestParam List<String> userNames){
		//this.maumau = mauMauService.handleGameStart(userNames, rules, 5);
		this.maumau.setPlayers(this.userService.createUsers(userNames));
		Map<String, String> response = new HashMap<String, String>();
		response.put("success", "" + userNames.size() + " created.");
		return response;
	}*/
	/*@PostMapping(path = "/test", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<String, String> easyRespond(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) {
		Map<String, String> response = new HashMap<String, String>();
		response.put("responseFirstname", firstname);
		response.put("responseLastname", lastname);
		return response;
	}*/
}
