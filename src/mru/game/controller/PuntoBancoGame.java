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
	
	/*
	 * Because i'm lazy, the game holds a spot in the arraylist instead of an actual player object. 
	 */
	public PuntoBancoGame(Player player) {
		this.player = player; 
		input = new Scanner(System.in);
		deck = new CardDeck();
		
	}
	
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
				countPoints(bankerHand) == 9) {
			displayResults();
			
		} else if (countPoints(playerHand) <= 5) {
			playerHand.add(drawCard());
			getBankerAction(true);
			displayResults();
		} else {
			getBankerAction(false);
			displayResults();
		}
		
		tallyPoints();
	}
	
	
	public void getBet() {
		System.out.print("How much do you want to bet this round? ");
		betAmount = input.nextInt();
		while (betAmount > player.getBalance()) {
			System.out.println("You can't afford that! Enter a bet equal to or less than $"+player.getBalance()+":");
			betAmount = input.nextInt();
		}
	}
	
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
	
	
	public Card drawCard() {
		if(deck.getDeck().size() <= 0) {
			deck = new CardDeck();
			System.out.print("\n\n\nDECK IS EMPTY! CREATING NEW DECK!\n\n\n");
		}
		Card drawnCard = new Card(deck.getDeck().get(0).getRank(),deck.getDeck().get(0).getSuit());
		deck.getDeck().remove(0);
		return drawnCard; 
	}

	/*
	 * Iterates through a player's hand and adds all their cards to a sum. Cards with face ranks or rank 10 are ignored.
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
	
	
	public char promptRepeat() {
		System.out.print("\nDo you want to play again?(Y/N) ");
		input.nextLine();
		char again = input.nextLine().toLowerCase().charAt(0);
		return again;
	}
	
	
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
