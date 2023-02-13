package mru.game.view;

import java.util.Scanner;

import mru.game.model.Player;

public class AppMenu {

	/**
	 * This class will be used to show the menus and sub menus to the user
	 * It also prompts the user for the inputs and validates them 
	 */
	
	Scanner input;
	
	/**
	 * Constructor class.
	 * Creates Scanner for user input.
	 * @return Doesn't return anything.
	 */
	public AppMenu() {
		input = new Scanner(System.in);
	}
	
	/**
	 * Prints out main menu.
	 * Takes and stores user input first character into variable "option".
	 * @param char  Takes user first character input.
	 * @return returns user input's first character.
	 */
	public char showMainMenu() {
		
		System.out.println("Select one of these options: ");
		System.out.println("");
		System.out.println("\t(P) Play Game");
		System.out.println("\t(S) Search");
		System.out.println("\t(E) Save and Exit");
		System.out.println("");
		System.out.print("Enter a choice: ");
		
		char option = input.nextLine().toLowerCase().charAt(0);
		return option;
	}
	
	/**
	 * Prints out Sub Menu.
	 * Takes and stores user input first character into variable "option".
	 * @param char  Takes user first character input.
	 * @return returns user input's first character.
	 */
	public char showSubMenu() {
		
		System.out.println("Select one of these options: ");
		System.out.println("");
		System.out.println("\t(T) Top Player (Most Number of Wins)");
		System.out.println("\t(N) Looking for a Name");
		System.out.println("\t(B) Back to Main Menu");
		System.out.println("");
		System.out.print("Enter a choice: ");
		
		char option = input.nextLine().toLowerCase().charAt(0);
		return option;
	}
	
	/**
	 * Asks user for name.
	 * Takes and stores user input into variable "name".
	 * @param String  Takes user input.
	 * @return returns user input.
	 */
	public String promptName() {
		
		System.out.print("Enter a name here: ");
		String name = input.nextLine().trim();
		return name;
	}
	
	/**
	 * Asks user for name.
	 * Takes and stores user input into variable "name".
	 * @param String  Takes user input.
	 * @return returns user input.
	 */
	public String inquireName() {
		
		System.out.print("What is your name: ");
		String name = input.nextLine().trim();
		return name;
	}
	
	/**
	 * Takes in existing player information.
	 * Prints out player information.
	 * @param Player  Takes information from file.
	 * @return Doesn't return anything.
	 */
	public void welcomeExistingPlayer(Player ply) {
		System.out.println("\n\n********************************************************************");
		System.out.printf("***   Welcome back %-9s---     Your balance is: %-5d$      ***%n",ply.getName(),ply.getBalance());
		System.out.println("********************************************************************");
	}
	
	/**
	 * Takes in user inputed name.
	 * Prints out user inputed name with default balance.
	 * @param String  User inputed String.
	 * @return Doesn't return anything.
	 */
	public void welcomeNewPlayer(String name) {
		System.out.println("\n\n********************************************************************");
		System.out.printf("***   Welcome %-9s---     Your initial balance is: 100 $    ***%n",name);
		System.out.println("********************************************************************");
		
	}
	
	/**
	 * Takes in player information.
	 * Prints out player information.
	 * Calls promptContinue method.
	 * @param Player  Takes information from file.
	 * @return Doesn't return anything.
	 */
	public void showPlayer(Player ply) {
		
		System.out.println("\n\n                   - PLAYER INFO -                   ");
		System.out.println("+================+================+================+");
		System.out.println("|NAME            |# WINS          |BALANCE         |");
		System.out.println("+================+================+================+");
		System.out.printf ("|%-16s|%-16d|%-14d$ |%n",ply.getName(),ply.getNumOfWins(),ply.getBalance());
		System.out.println("+================+================+================+");
		promptContinue();
	}
	
	/**
	 * Asks user for input to proceed.
	 * @return Doesn't return anything.
	 */
	public void promptContinue() {
		System.out.println("Press \"Enter\" to continue...");
		input.nextLine();
	}
	
	/**
	 * Displays game type menu.
	 * Prints out player information.
	 * @param char  Takes user input's first character.
	 * @return returns user's first input character.
	 */
	public char promptGameType() {
		
		System.out.println("Who do you want to bet on?\n");
		System.out.println("\t(P) Player Wins");
		System.out.println("\t(B) Banker Wins");
		System.out.println("\t(T) Tie Game");
		System.out.println("");
		System.out.print("Enter your choice: ");
		
		char option = input.nextLine().toLowerCase().charAt(0);
		return option;
	}
	
	
	
}
