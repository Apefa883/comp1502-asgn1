package mru.game.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import mru.game.model.Player;

public class PuntoBancoGame {
	
	private char winState;
	Player player;
	Scanner input;
	private int betAmount; 
	boolean flag;
	CardDeck deck;
	ArrayList<Card> bankerHand;
	ArrayList<Card> playerHand;
	
	/**
	 * Constructor class. Grabs a player by reference and creates a CardDeck object to draw cards from.
	 * @return no return. Deck and input are created here and used throughout the class.
	 */
	public PuntoBancoGame(Player player) {
		this.player = player; 
		input = new Scanner(System.in);
		deck = new CardDeck();
		
	}
	
	/**
	 * Creates hands for the player and banker and manages the logic of the game itself.
	 * Manages if and how the player draws their cards, and calls getBankerAction() to get the banker's response
	 * Calls TallyPoints() to tally up the points.
	 * @throws IOException
	 */
	public void playRound() throws IOException {
		winState = getGameType();
		getBet();
		bankerHand = new ArrayList<>();
		playerHand = new ArrayList<>();
		int i;
		//Step 1 - Deal 2 cards to both player and banker
		for(i=0;i<2;i++) {
			bankerHand.add(drawCard());
			playerHand.add(drawCard());
		}
		
		//Step 2 - Check both parties' points
		if(countPoints(playerHand) == 8 || countPoints(playerHand) == 9 || countPoints(bankerHand) == 8 ||
				countPoints(bankerHand) == 9) { //If either has a total of 8 or 9, skip to displaying the results
			displayResults();
			
		} else if (countPoints(playerHand) <= 5) { //If the player's total is low, draws a new card for them.
			playerHand.add(drawCard());
			getBankerAction(true); //Runs the banker's next move with the knowledge that the player drew a new card.
			displayResults();
		} else {
			getBankerAction(false); //Runs the banker's next move with the knowledge that the player did not draw a new card.
			displayResults();
		}
		
		tallyPoints(); //Tallies the points and displays the result
	}
	
	
	/**
	 * Inputs the user's chosen bet amount.
	 * @param Takes user input to find the bet amount
	 */
	public void getBet() {
		System.out.print("How much do you want to bet this round? ");
		betAmount = input.nextInt();
		while (betAmount > player.getBalance()) {
			System.out.println("You can't afford that! Enter a bet equal to or less than $"+player.getBalance()+":");
			betAmount = input.nextInt();
		}
	}
	
	
	/**
	 * Checks which participant the user bets on: either a player win, a banker win or a tie
	 * @return Returns the user's choice
	 */
	public char promptGameType() {
		System.out.println("\nWho do you want to bet on?\n");
		System.out.println("\t(P) Player Wins");
		System.out.println("\t(B) Banker Wins");
		System.out.println("\t(T) Tie Game");
		System.out.println("");
		System.out.print("Enter your choice: ");
		char option = input.nextLine().toLowerCase().charAt(0);
		return option;
	}
	
	
	/**
	 * Prompts for and interprets the user's choice of win condition and applies it to a global variable.
	 * Calls promptGameType() to display the menu for user selection
	 * @return returns the player's selection
	 */
	private char getGameType() throws IOException {
		
		flag = true;
		char option = 'e';
		
		while (flag) {
			option = promptGameType();
			
			switch (option) {
			case 'p':
				flag = false;
				break;
			case 'b':
				flag = false;
				break;
			case 't':
				flag = false;
				break;
			}
		}
		return option;
	}
	
	
	/**
	 * Duplicates a card from the deck, then removes it from the deck.
	 * @return returns the drawn card
	 */
	public Card drawCard() {
		if(deck.getDeck().size() <= 0) {
			deck = new CardDeck();
			System.out.print("\n\n\nDECK IS EMPTY! CREATING NEW DECK!\n\n\n");
		}
		Card drawnCard = new Card(deck.getDeck().get(0).getRank(),deck.getDeck().get(0).getSuit());
		deck.getDeck().remove(0);
		return drawnCard; 
	}

	/**
	 * @param Takes an arraylist of type Card.
	 * Iterates through a player's hand and adds all their cards to a sum. Cards with face ranks or rank 10 are ignored.
	 * @return returns the amount of points in a given hand of cards.
	 */
	public int countPoints(ArrayList<Card> hand) {
		int sum = 0;
		for (int i = 0; i < hand.size(); i++) {
			if(hand.get(i).getRank() < 10) {
				sum += hand.get(i).getRank();
			}
		}
		return (sum % 10);
	}
	
	/**
	 * Governs the actions of the banker, based on the points that the player and banker have earned.
	 * If the player and banker both meet conditions relative to oneanother, the banker draws a new card.
	 * @param playerDrew whether or not the player drew a card
	 */
	public void getBankerAction(boolean playerDrew) {
		if(playerDrew) {
			if(playerHand.get(2).getRank() <= 3 && playerHand.get(2).getRank() > 1) {
				if(countPoints(bankerHand) <= 4) {
					bankerHand.add(drawCard());
				}
			} else if (playerHand.get(2).getRank() <= 5) {
				if(countPoints(bankerHand) <= 5) {
					bankerHand.add(drawCard());
				}
			} else if (playerHand.get(2).getRank() <= 7) {
				if(countPoints(bankerHand) <= 6) {
					bankerHand.add(drawCard());
				}
			} else if (playerHand.get(2).getRank() == 8) {
				if(countPoints(bankerHand) <= 2) {
					bankerHand.add(drawCard());
				}
			} else if (playerHand.get(2).getRank() == 1 || playerHand.get(2).getRank() > 8) {
				if(countPoints(bankerHand) <= 3) {
					bankerHand.add(drawCard());
				}
			}
		} else {
			if(countPoints(bankerHand) <= 5) {
				bankerHand.add(drawCard());
			}
		}
	}
	
	/**
	 * Displays the cards that the player and banker have drawn
	 * No parameters or returns. Purely visual.
	 */
	public void displayResults() {
		System.out.printf("%n                     - PUNTO BANCO -                     %n");
		System.out.printf("+===========================+===========================+%n");
		System.out.printf("| PLAYER                    | BANKER                    |%n");
		System.out.printf("+===========================+===========================+%n");
		System.out.printf("|%-27s|%-27s|%n",playerHand.get(0),bankerHand.get(0));
		System.out.printf("+===========================+===========================+%n");
		System.out.printf("|%-27s|%-27s|%n",playerHand.get(1),bankerHand.get(1));
		System.out.printf("+===========================+===========================+%n");
		if(playerHand.size() > 2) {
			System.out.printf("|%-27s|",playerHand.get(2));
		} else {
			System.out.printf("|                           |");
		}
		
		if(bankerHand.size() > 2) {
			System.out.printf("%-27s|%n",bankerHand.get(2));
		} else {
			System.out.printf("                           |%n");
		}
		System.out.printf("+===========================+===========================+%n");
		System.out.printf("| PLAYER POINTS: %-5d      | BANKER POINTS: %-5d      |%n",countPoints(playerHand),
				countPoints(bankerHand));
		System.out.printf("+===========================+===========================+%n");
	}
	
	/**
	 * Tallies up the points of the player and banker, and displays whether or not the user has won.
	 * Also increases the user's win count and wealth if they win, and takes away wealth if they lose.
	 * @throws IOException
	 */
	public void tallyPoints() throws IOException {
		
		System.out.printf("%n              $$$$$$$$$$$$$$$$$$$$$$$$$$$$              %n");
		
		if(awardPoints()) {
			player.setNumOfWins(player.getNumOfWins() + 1);
			if(winState == 't') {
				System.out.printf("              $      PLAYER WON %d$      $              %n",betAmount*5);
				player.setBalance(player.getBalance() + (5*betAmount));
			} else {
				System.out.printf("              $      PLAYER WON %d$      $              %n",betAmount);
				player.setBalance(player.getBalance() + betAmount);
			}
		} else {
			if(winState == 't') {
				System.out.printf("              $     PLAYER LOST %d$      $              %n",betAmount*5);
				player.setBalance(player.getBalance() - (5*betAmount));
			} else {
				System.out.printf("              $     PLAYER LOST %d$      $              %n",betAmount);
				player.setBalance(player.getBalance() - betAmount);
			}
			if (player.getBalance() < 0) {
				player.setBalance(0);
			}
		}
		System.out.printf("              $$$$$$$$$$$$$$$$$$$$$$$$$$$$              %n%n");
		char again = promptRepeat();
		if (again == 'y') {
			playRound();
		}
	}
	
	/**
	 * Asks the user if they wish to start another round
	 * @return returns the user's choice
	 */
	public char promptRepeat() {
		System.out.print("\nDo you want to play again?(Y/N) ");
		input.nextLine();
		char again = input.nextLine().toLowerCase().charAt(0);
		return again;
	}
	
	/**
	 * Checks if the user has won
	 * @return whether or not the user has won
	 */
	public boolean awardPoints() {
		boolean youWon = false;
		if(winState=='p') {
			if(countPoints(playerHand) > countPoints(bankerHand)) {
				youWon = true;
			}
		} else if (winState=='b') {
			if(countPoints(bankerHand) > countPoints(playerHand)) {
				youWon = true;
			}
		} else if (winState=='t') {
			if(countPoints(bankerHand) == countPoints(playerHand)) {
				youWon = true;
			}
		}
		return youWon;
	}
	
	
	/**
	 * In this class you implement the game
	 * You should use CardDeck class here
	 * See the instructions for the game rules
	 */

}
