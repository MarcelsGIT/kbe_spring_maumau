
var customCards = (function() {
	//The global options
	var opt = {
		cardSize : {width:69,height:94, padding:18},
		animationSpeed : 500,
		table : 'body',
		cardback : 'red',
		acesHigh : false,
		cardsUrl : 'img/cards.png',
		blackJoker : false,
		redJoker : false
	};
	var zIndexCounter = 1;
	var all = []; //All the cards created.
	
	/*
	global events
	*/
	function mouseEvent(ev) {
		var card = $(this).data('card');
		if (card.container) {
			var handler = card.container._click;
			if (handler) {
				handler.func.call(handler.context||window, card, ev);
			}
		}
	}
	/*
	global init
	*/
	function init(options) {
		if (options) {
			for (var i in options) {
				if (opt.hasOwnProperty(i)) {
					opt[i] = options[i];
				}
			}
		}
		var start = opt.acesHigh ? 2 : 1;
		var end = start + 12;
		opt.table = $(opt.table)[0];
		if ($(opt.table).css('position') == 'static') {
			$(opt.table).css('position', 'relative');
		}
		for (var i = start; i <= end; i++) {
			if(i == 2 || i == 3 || i == 4 || i == 5 || i == 6){
				continue;
			}
			all.push(new Card('h', i, opt.table));
			all.push(new Card('s', i, opt.table));
			all.push(new Card('d', i, opt.table));
			all.push(new Card('c', i, opt.table));
		}
		if (opt.blackJoker) {
			all.push(new Card('bj', 0, opt.table));
		}
		if (opt.redJoker) {
			all.push(new Card('rj', 0, opt.table));
		}
		
		$('.card').click(mouseEvent);
		shuffle(all);
	}
	/**
	globalShuffle
	*/
    function shuffle(deck) {
        //Fisher yates shuffle
        var i = deck.length;
        if (i == 0) return;
        while (--i) {
            var j = Math.floor(Math.random() * (i + 1));
            var tempi = deck[i];
            var tempj = deck[j];
            deck[i] = tempj;
            deck[j] = tempi;
        }
    }
	
	function Card(suit, rank, table) {
		this.init(suit, rank, table);
	}
	/*
	This is the Cards Object
	*/
	Card.prototype = {
		init: function (suit, rank, table) {
			this.shortName = suit + rank;
			this.suit = suit;
			this.rank = rank;
			this.name = suit.toUpperCase()+rank;
			this.faceUp = false;
			this.el = $('<div/>').css({
				width:opt.cardSize.width,
				height:opt.cardSize.height,
				"background-image":'url('+ opt.cardsUrl + ')',
				position:'absolute',
				cursor:'pointer'	
			}).addClass('card').data('card', this).appendTo($(table));
			this.showCard();
			this.moveToFront();
		},

		toString: function () {
			return this.name;
		},

		moveTo : function(x, y, speed, callback) {
			var props = {top:y-(opt.cardSize.height/2),left:x-(opt.cardSize.width/2)};
			$(this.el).animate(props, speed || opt.animationSpeed, callback);
		},
		
		rotate : function(angle) {
			$(this.el)
				.css('-webkit-transform', 'rotate(' + angle + 'deg)')
				.css('-moz-transform', 'rotate(' + angle + 'deg)')
				.css('-ms-transform', 'rotate(' + angle + 'deg)')
				.css('transform', 'rotate(' + angle + 'deg)')
				.css('-o-transform', 'rotate(' + angle + 'deg)');
		},
		
		showCard : function() {
			var offsets = { "c": 0, "d": 1, "h": 2, "s": 3 };
			var xpos, ypos;
			var rank = this.rank;
			if (rank == 14) {
				rank = 1; //Aces high must work as well.
			}
			xpos = -rank * opt.cardSize.width;
			ypos = -offsets[this.suit] * opt.cardSize.height;
			this.rotate(0);
			$(this.el).css('background-position', xpos + 'px ' + ypos + 'px');
		},

		hideCard : function(position) {
			var y = opt.cardback == 'red' ? 0*opt.cardSize.height : -1*opt.cardSize.height;
			$(this.el).css('background-position', '0px ' + y + 'px');
			this.rotate(0);
		},
		
		moveToFront : function() {
			$(this.el).css('z-index', zIndexCounter++);
		}		
	};
	/**
	THe Container Object, parent of deck
	*/
	function Container() {
	
	}
	
	Container.prototype = new Array();
	Container.prototype.extend = function(obj) {
		for (var prop in obj) {
			this[prop] = obj[prop];
		}
	}
	Container.prototype.extend({
		/*
		Add aa certain card
		*/
		addCard : function(card) {
			this.addCards([card]);
		},
		/*
		add an array of cards
		*/
		addCards : function(cards) {
			for (var i = 0; i < cards.length;i++) {
				var card = cards[i];
				if (card.container) {
					card.container.removeCard(card);
				}
				this.push(card);
				card.container = this;
			}
		},
		/*
		remove a card
		*/
		removeCard : function(card) {
			for (var i=0; i< this.length;i++) {
				if (this[i] == card) {
					this.splice(i, 1);
					return true;
				}
			}
			return false;
		},
		/*
		init (display options for deck)
		*/
		init : function(options) {
			options = options || {};
			this.x = options.x || $(opt.table).width()/2;
			this.y = options.y || $(opt.table).height()/2;
			this.faceUp = options.faceUp;
		},
		/*
		click events
		*/
		click : function(func, context) {
			this._click = {func:func,context:context};
		},
		/*
		mouse down events
		*/
		mousedown : function(func, context) {
			this._mousedown = {func:func,context:context};
		},
		/*
		mouse up events
		*/
		mouseup : function(func, context) {
			this._mouseup = {func:func,context:context};
		},
		/*
		displaying the deck
		*/	
		render : function(options) {
			options = options || {};
			var speed = options.speed || opt.animationSpeed;
			this.calcPosition(options);
			for (var i=0;i<this.length;i++) {
				var card = this[i];
				zIndexCounter++;
				card.moveToFront();
				var top = parseInt($(card.el).css('top'));
				var left = parseInt($(card.el).css('left'));
				if (top != card.targetTop || left != card.targetLeft) {
					var props = {top:card.targetTop, left:card.targetLeft, queue:false};
					if (options.immediate) {
						$(card.el).css(props);
					} else {
						$(card.el).animate(props, speed);
					}
				}
			}
			var me = this;
			var flip = function(){
				for (var i=0;i<me.length;i++) {
					if (me.faceUp) {
						me[i].showCard();
					} else {
						me[i].hideCard();
					}
				}
			}
			if (options.immediate) {
				flip();
			} else {
				setTimeout(flip, speed /2);
			}
			
			if (options.callback) {
				setTimeout(options.callback, speed);
			}
		},
		/*
		render a certain card
		*/
		renderCertainCard : function(options) {
			options = options || {};
			var speed = options.speed || opt.animationSpeed;
			this.calcPosition(options);
			console.log("renderCertainCard")
			//for (var i=0;i<this.length;i++) {
				var card = this.getCardByValue(options.card.suit, options.card.rank);
				zIndexCounter++;
				card.moveToFront();
				var top = parseInt($(card.el).css('top'));
				var left = parseInt($(card.el).css('left'));
				if (top != card.targetTop || left != card.targetLeft) {
					var props = {top:card.targetTop, left:card.targetLeft, queue:false};
					if (options.immediate) {
						$(card.el).css(props);
					} else {
						$(card.el).animate(props, speed);
					}
				}
			//}
			var me = this;
			var flip = function(){
				for (var i=0;i<me.length;i++) {
					if (me.faceUp) {
						me[i].showCard();
					} else {
						me[i].hideCard();
					}
				}
			}
			if (options.immediate) {
				flip();
			} else {
				setTimeout(flip, speed /2);
			}
			
			if (options.callback) {
				setTimeout(options.callback, speed);
			}
		},
		/*
		get the top card 
		*/
		topCard : function() {
			return this[this.length-1];
		},
		
		toString: function() {
			return 'Container';
		},

		getCardByValue: function(suit, rank){
			for(i = 0; i < this.length; i++){
				if(this[i].rank == rank && this[i].suit == suit) return this[i];
			}
			return undefined;
		}
	});
	
	function Deck(options) {
		this.init(options);
	}
	
	Deck.prototype = new Container();
	Deck.prototype.extend({
		/*
		display the deck
		*/
		calcPosition : function(options) {
			options = options || {};
			var left = Math.round(this.x-opt.cardSize.width/2, 0);
			var top = Math.round(this.y-opt.cardSize.height/2, 0);
			var condenseCount = 6;
			for (var i=0;i<this.length;i++) {
				if (i > 0 && i % condenseCount == 0) {
					top-=1;
					left-=1;
				}
				this[i].targetTop = top;
				this[i].targetLeft = left;
			}
		},
		
		toString : function() {
			return 'Deck';
		},
		/*
		deal random cards
		*/
		deal : function(count, hands, speed, callback) {
			var me = this;
			var i = 0;
			var totalCount = count*hands.length;
			function dealOne() {
				if (me.length == 0 || i == totalCount) {
					if (callback) {
						callback();
					}
					return;
				}
				hands[i%hands.length].addCard(me.topCard());
				hands[i%hands.length].render({callback:dealOne, speed:speed});
				i++;
			}
			dealOne();
		},
		/*
		deal given cards
		JSON Object should look like this: 
		{hand1: { 0: { suit: 'h' rank: 7 }}}
		*/
		dealCertainCards: function(count, hand, cards, callback){
			console.log("dealCertainCards")
			var me = this;
			var i = 0;
			function dealOne(){
				console.log("dealOne1")
				if (me.length == 0 || i == count) {
					if (callback) {
						callback();
					}
					return;
				}
				console.log("Card", cards[i].value.suit, cards[i].value.rank)
				hand.addCard(me.getCardByValue(cards[i].value.suit, cards[i].value.rank));
				hand.renderCertainCard({callback:dealOne, speed:50, card: {suit:cards[i].value.suit, rank:cards[i].value.rank}});
				i++;
			}
			dealOne()
			
		}
	});

	function Hand(options) {
		this.init(options);
	}
	Hand.prototype = new Container();
	Hand.prototype.extend({
		calcPosition : function(options) {
			options = options || {};
			var width = opt.cardSize.width + (this.length-1)*opt.cardSize.padding;
			var left = Math.round(this.x - width/2);
			var top = Math.round(this.y-opt.cardSize.height/2, 0);
			for (var i=0;i<this.length;i++) {
				this[i].targetTop = top;
				this[i].targetLeft = left+i*opt.cardSize.padding;
			}
		},
		
		toString : function() {
			return 'Hand';
		}
	});
	
	function Pile(options) {
		this.init(options);
	}
	
	Pile.prototype = new Container();
	Pile.prototype.extend({
		calcPosition : function(options) {
			options = options || {};
		},
		
		toString : function() {
			return 'Pile';
		},
		
		deal : function(count, hands) {
			if (!this.dealCounter) {
				this.dealCounter = count * hands.length;
			}
		}
	});
	

	return {
		init : init,
		all : all,
		options : opt,
		SIZE : opt.cardSize,
		Card : Card,
		Container : Container,
		Deck : Deck,
		Hand : Hand,
		Pile : Pile,
		shuffle: shuffle
	};
})();

if (typeof module !== 'undefined') {
    module.exports = cards;
}

