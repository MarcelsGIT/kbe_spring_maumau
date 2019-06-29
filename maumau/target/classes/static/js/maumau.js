$('#deal').hide();
customCards.init({table:'#card-table'}); //initialize the table (the "field" of the game)
deck = new customCards.Deck(); //Create a new deck of cards

upperhand = new customCards.Hand({faceUp:true, y:60}); //create a hand, face up.
lowerhand = new customCards.Hand({faceUp:true, y:340}); //create a hand, face up.
var hands = [upperhand, lowerhand] //an array of hands

discardPile = new customCards.Deck({faceUp:true});//create a discard pile

var setupGame = function(){
	return new Promise(function(resolve, reject){
		$.ajax({
						type: 'POST',
						contentType: 'application/json',
						url: '/setupGame',
						success: function(data, status){
							console.log(data, status)
							rDeck = data.decks.deck; // get the deck from the server
							rGY = data.decks.graveyard // get the graveyard from the server
							rUsers = data.users // get the user from the server
							rProps = data.props // get the game properties

							
							deck.x -= 50; //By default it's in the middle of the container, put it slightly to the side
							deck.addCards(customCards.all); //cards.all contains all cards, put them all in the deck
							deck.render({immediate:true}); //No animation here, just get the deck onto the table.

							discardPile.x += 50;//set x of discard pile (50px right, next to the deck)

							console.log("discardPile")
							discardPile.addCard(deck.getCardByValue(rGY.cards[Object.keys(rGY.cards).length -1].value.suit, 
								rGY.cards[Object.keys(rGY.cards).length -1].value.rank));
							discardPile.render();

							
							for(i = 0; i < hands.length; i++){
								console.log("User Key", Object.keys(rUsers)[i])
								hands[i]["userName"] = Object.keys(rUsers)[i]
								deck.dealCertainCards(5, hands[i], rUsers[Object.keys(rUsers)[i]].hand)
							}

							
						},
						error: function(xhr, status, err){
							console.log(xhr, status, err)
						}
					})

	})
};

/*var takeCard = function(){
	return new Promise(function(resolve, reject){
		$.ajax({
						type: 'POST',
						contentType: 'application/json',
						url: '/takeCard',
						success: function(data, status){
							console.log(data, status)
							rDeck = data.decks.deck; // get the deck from the server
							rGY = data.decks.graveyard // get the graveyard from the server
							rUsers = data.users // get the user from the server
							rProps = data.props // get the game properties

							for(i = 0; i < hands);
							
						},
						error: function(xhr, status, err){
							console.log(xhr, status, err)
						}
					})
	})
}*/

setupGame();
//When you click on the top card of a deck, a card is added
//to your hand
deck.click(function(card){
	if (card === deck.topCard()) {
		lowerhand.addCard(deck.topCard());
		lowerhand.render();
	}
});

//Finally, when you click a card in your hand, if it's
//the same suit or rank as the top card of the discard pile
//then it's added to it
lowerhand.click(function(card){
	if (card.suit == discardPile.topCard().suit 
		|| card.rank == discardPile.topCard().rank) {
		console.log(card.suit)
		console.log(card.rank)
		discardPile.addCard(card);
		discardPile.render();
		lowerhand.render();
	}
});

upperhand.click(function(card){
	if (card.suit == discardPile.topCard().suit 
		|| card.rank == discardPile.topCard().rank) {
		discardPile.addCard(card);
		discardPile.render();
		lowerhand.render();
	}
});

/*
Helper Methods
*/
var getUrlParameter = function(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};

/*				
				$('#deal').on('click', function(e){
					
					var param = {userNames: 'marcel', userNames: 'decker'}
					console.log($.param(param))
					$.ajax({
						type: 'POST',
						contentType: 'application/json',
						url: '/instanciateUsers?' + $.param(param),
						success: function(data, status){
							console.log("success")
							console.log(data)
							console.log(status)
						},
						error: function(xhr, status, err){
							console.log(xhr, status, err)
						}
					})
				})
			})*/






//Let's deal when the Deal button is pressed:
/*$('#deal').click(function() {
	//Deck has a built in method to deal to hands.
	$('#deal').hide();
	$.ajax({
	
						type: 'POST',
						contentType: 'application/json',
						url: '/instanciateUsers?' + result,
						success: function(data, status){
							console.log(data)
							deck.deal(5, [upperhand, lowerhand], 50, function() {
							//This is a callback function, called when the dealing
							//is done.
							discardPile.addCard(deck.topCard());
							discardPile.render();
	});
						},
						error: function(xhr, status, err){
							console.log(xhr, status, err)
						}
		})
	
});*/


//So, that should give you some idea about how to render a card game.
//Now you just need to write some logic around who can play when etc...
//Good luck :)