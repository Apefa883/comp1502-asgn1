package mru.game.model;

public class Player {
	
	/**
	 * This class represent each player record in the Database
	 * It is basically a model class for each record in the txt file
	 */
	
	String name;
	int balance;
	int numOfWins;
	
	/**
	 * Constructor class.
	 * Takes in list information to be able assign the information to variables.
	 * @return no return value.
	 */	
	public Player(String name, int balance, int numOfWins) {
		
		this.name = name;
		this.balance = balance;
		this.numOfWins = numOfWins;
	}
	
	/**
	 * Takes in player name.
	 * Sets name to variable "name".
	 * @param String  Takes in player name.
	 * @return Doesn't return anything.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * returns the value previously set in corresponding get method.
	 * @return returns player name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Takes in player balance.
	 * Sets integer to variable "balance".
	 * @param int  Takes in player int balance.
	 * @return Doesn't return anything.
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	/**
	 * returns the value previously set in corresponding get method.
	 * @return returns the balance.
	 */
	public int getBalance() {
		return balance;
	}
	
	/**
	 * Takes in player wins.
	 * Sets name to variable "name".
	 * @param int  Takes in player number of wins.
	 * @return Doesn't return anything.
	 */
	public void setNumOfWins(int numOfWins) {
		this.numOfWins = numOfWins;
	}
	
	/**
	 * returns the value previously set in corresponding get method.
	 * @return returns player's number of wins.
	 */
	public int getNumOfWins() {
		return numOfWins;
	}
	
	/**
	 * Takes and Displays the set user information in a String.
	 * @return returns player information in form of a String.
	 */
	public String toString() {
		return "Name: " + name + " Balance: " + balance + " Number of Wins: " + numOfWins;
	}
	
	/**
	 * Takes and Displays the set user information in a String.
	 * @return returns player information in form of a String.
	 */
	public String format() {
		return name+","+balance+","+numOfWins;
	}
	
}
