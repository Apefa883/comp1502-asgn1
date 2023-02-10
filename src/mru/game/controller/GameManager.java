package mru.game.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
	
	public GameManager() throws Exception {
		players = new ArrayList<>();
		appMen = new AppMenu();
		loadData();
		lunchApplication();
	}

	private void lunchApplication() throws IOException {
		
		boolean flag = true;
		char option;
		
		while (flag) {
			option = appMen.showMainMenu();
			
			switch (option) {
			case 'p':
				playGame();
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
	
	private void playGame() {
		// TODO Auto-generated method stub
		
	}

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

	private Player searchByName() {
	
		String name = appMen.promptName();
		Player ply = null;
		
		for (Player p: players) {
			
			if (p.getName().toLowerCase().equals(name.toLowerCase())) {
				ply = p;
				System.out.println("");
				System.out.println("Player Found!");
				appMen.showPlayer(ply);
				System.out.println("");
				break;
			}
			else {
				System.out.println("");
				System.out.println("Player not found!");
				System.out.println("");
				break;
			}
		}
		
		return ply;
	}

	private Player findTopPlayer() { // NEED TO IMPLEMENT

		Player ply = null;
//		int wins = Integer.parseInt(players.get(2));
//		Player ply = Collections.max(players.get(2));
		return ply;
	}

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

}
