package mru.game.view;

import java.io.IOException;
import java.util.Scanner;

import mru.game.model.Player;

public class AppMenu {

	/**
	 * This class will be used to show the menus and sub menus to the user
	 * It also prompts the user for the inputs and validates them 
	 */
	
	Scanner input;
	
	public AppMenu() {
		input = new Scanner(System.in);
	}
	
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
	
	public String promptName() {
		
		System.out.print("Enter a name here: ");
		String name = input.nextLine().trim();
		return name;
	}
	
	public String inquireName() {
		
		System.out.print("What is your name: ");
		String name = input.nextLine().trim();
		return name;
	}
	
	
	public void welcomeExistingPlayer(Player ply) {
		System.out.println("\n\n********************************************************************");
		System.out.printf("***   Welcome back %-9s---     Your balance is: %-5d$      ***%n",ply.getName(),ply.getBalance());
		System.out.println("********************************************************************");
	}
	
	
	public void welcomeNewPlayer(String name) {
		System.out.println("\n\n********************************************************************");
		System.out.printf("***   Welcome %-9s---     Your initial balance is: 100 $    ***%n",name);
		System.out.println("********************************************************************");
		
	}
	
	
	public void showPlayer(Player ply) {
		
		System.out.println("\n\n                   - PLAYER INFO -                   ");
		System.out.println("+================+================+================+");
		System.out.println("|NAME            |# WINS          |BALANCE         |");
		System.out.println("+================+================+================+");
		System.out.printf ("|%-16s|%-16d|%-14d$ |%n",ply.getName(),ply.getNumOfWins(),ply.getBalance());
		System.out.println("+================+================+================+");
		promptContinue();
	}
	
	
	public void promptContinue() {
		System.out.println("Press \"Enter\" to continue...");
		input.nextLine();
	}
	
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
