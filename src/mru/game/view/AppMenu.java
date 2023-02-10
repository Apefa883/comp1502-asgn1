package mru.game.view;

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
	
	public void showPlayer(Player ply) {
		
		System.out.println(ply);
	}
	
}
