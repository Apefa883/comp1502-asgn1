package mru.game.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import mru.game.model.Player;
import mru.game.view.AppMenu;

public class GameManager {
	
	/* In this class toy'll need these methods:
	 * A constructor
	 * A method to load the txt file into an arraylist (if it exists, so you check if the txt file exists first)
	 * A save method to store the arraylist into the the txt file 
	 * A method to search for a player based their name
	 * A method to find the top players
	 * Depending on your designing technique you may need and you can add more methods here 
	 */
	
	private final String FILE_PATH = "res/CasinoInfo.txt";
	ArrayList<Player> players;
	AppMenu appMen;
	public boolean flag;
	PuntoBancoGame gameLogic;
	
	
	/**
	 * Constructor class. 
	 * Assigns an ArrayList to variable "players".
	 * Assigns AppMenu Class to variable "appMen".
	 * calls loadData and lunchApplication methods.
	 * @throws Exception
	 * @return Doesn't return anything.
	 */
	public GameManager() throws Exception {
		players = new ArrayList<>();
		appMen = new AppMenu();
		loadData();
		lunchApplication();
	}

	
	/**
	 * Takes information from AppMenu and calls methods according to the given information.
	 * @throws IOSException
	 * @return Doesn't return anything.
	 */
	private void lunchApplication() throws IOException {
		
		flag = true;
		char option;
		
		while (flag) {
			option = appMen.showMainMenu();
			
			switch (option) {
			case 'p':
				playGame();
				flag=false;
				break;
			case 's':
				Search();
				break;
			case 'e':
				Save();
				flag = false;
			}
		}
	}
	
	/**
	 * Takes player information from logIn method and checks to see if player is qualified to play.
	 * If qualified, plays the game, if not returns to main menu.
	 * @throws IOSException
	 * @return Doesn't return anything.
	 */
	private void playGame() throws IOException {
		int playerSpot = logIn();
		if (playerSpot >= 0) {
			gameLogic = new PuntoBancoGame(players.get(playerSpot));
			gameLogic.playRound();
			Save();
		} else {
			System.out.print("\nYou are too broke to play! Returning to main menu...\n");
			lunchApplication();
		}
		
	}

	/**
	 * Takes information from AppMenu and calls methods according to the given information.
	 * @return Doesn't return anything.
	 */
	private void Search() {
		
		char option = appMen.showSubMenu();
		
		switch (option) {
		case 't':
			findTopPlayer();
			break;
		case 'n':
			searchByName();
			break;
		case 'b':
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * Takes information from AppMenu and searches for given information in CasinoInfo.txt file.
	 * Displays player information if found, if not found displays "Player does not exist!"
	 * @return Doesn't return anything.
	 */
	private Player searchByName() {
	
		String name = appMen.promptName();
		Player ply = null;
		
		for (int i = 0; i < players.size(); i++) {
			
			if (players.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
				ply = players.get(i);
				appMen.showPlayer(ply);
				break;
			}
		}
		if(ply == null) {
			System.out.print("\nPlayer does not exist!\n");
		}
		return ply;
	}

	/**
	 * Goes through the CasinoInfo.txt file and orders the top 3 players with most wins from top to bottom.
	 * Then displays the top 3 players and prompts the user for an input to continue.
	 * @return Doesn't return anything.
	 */
	private Player findTopPlayer() {

		Player ply = null;
		int firstPos = 0;
		int secondPos = 0;
		int thirdPos = 0;
		int firstWins = 0;
		int secondWins = 0;
		int thirdWins = 0;
		
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getNumOfWins() >= firstWins) {
				firstPos = i;
				firstWins = players.get(i).getNumOfWins();
			}
		}
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getNumOfWins() >= secondWins && 
					players.get(i).getNumOfWins() <= players.get(firstPos).getNumOfWins() && i != firstPos) {
				secondPos = i;
				secondWins = players.get(i).getNumOfWins();
			}
		}
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getNumOfWins() >= thirdWins && 
					players.get(i).getNumOfWins() <= players.get(secondPos).getNumOfWins() &&
					i != firstPos && i != secondPos) {
				thirdPos = i;
				thirdWins = players.get(i).getNumOfWins();
			}
		}
		
		System.out.println("\n\n               - TOP PLAYERS -               ");
		System.out.println("+=====================+=====================+");
		System.out.printf ("|%-21s|%-21d|%n",players.get(firstPos).getName(),players.get(firstPos).getNumOfWins());
		System.out.println("+=====================+=====================+");
		System.out.printf ("|%-21s|%-21d|%n",players.get(secondPos).getName(),players.get(secondPos).getNumOfWins());
		System.out.println("+=====================+=====================+");
		System.out.printf ("|%-21s|%-21d|%n",players.get(thirdPos).getName(),players.get(thirdPos).getNumOfWins());
		System.out.print("+=====================+=====================+\n\n");
		
		appMen.promptContinue();
		
		
		return ply;
	}

	/**
	 * Takes the returned String value from AppMenu's format method and writes it into the CasinoInfo.txt file.
	 * @throws IOSException
	 * @return Doesn't return anything.
	 */
	private void Save() throws IOException {
		File db = new File(FILE_PATH);
		PrintWriter pw = new PrintWriter(db);
		
		System.out.println("");
		System.out.println("Saving...");
		
		for (Player p: players) {
			pw.println(p.format());
		}
		
		pw.close();
		System.out.println("Done! Please visit us again!");
	}
	
	
	/**
	 * Searches for the user inputed name in CasinoInfo.txt. 
	 * If the name is found in the file, updates player information to the corresponding information found in the file.
	 * @throws Exception
	 * @return Doesn't return anything.
	 */
	private void loadData() throws Exception {
		
		File db = new File(FILE_PATH);
		String currentLine;
		String[] splittedLine;
		
		if (db.exists()) {
			
			Scanner fileReader = new Scanner(db);
			while (fileReader.hasNextLine()) {
				
				currentLine = fileReader.nextLine();
				splittedLine = currentLine.split(",");
				Player p = new Player(splittedLine[0], Integer.parseInt(splittedLine[1]), Integer.parseInt(splittedLine[2]));
				players.add(p);
			}
			
			fileReader.close();
		}
	}
	
	/**
	 * Searches for existing player, if exists, assigns found information to player and decides if player is eligible for play.
	 * If not existing player, creates a new player profile with the given name and default balance and win values.
	 * @return returns playerSpot
	 */
	public int logIn() {

		String name = appMen.inquireName();
		Player ply = null;
		int playerSpot = 0;
		
		for (int i = 0; i < players.size(); i++) {
			
			if (players.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
				ply = players.get(i);
				if(ply.getBalance() <= 0) {
					playerSpot = -1;
					break;
				}
				playerSpot = i;
				appMen.welcomeExistingPlayer(ply);
			}
		}
		if(ply == null) {
			playerSpot = players.size();
			players.add(new Player(name,100,0));
			appMen.welcomeNewPlayer(name);
		}
		return playerSpot;
	}
	

}
