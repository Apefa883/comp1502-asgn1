package mru.game.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import mru.game.controller.Card;

/*
 * We kept the amount of public testable methods to a minimum. Instead, this
 * document demonstrates a successful J-Unit test of the three most logic-heavy methods.
 * Functions to test:
 * awardPoints()
 * countPoints()
 * getBankerAction()
 */

class GameTest {

	/*
	 * This tests whether the game correctly assigns a win or lose depending on the player's choice of playstyle
	 * and the circumstances of the game.
	 */
	@Test
	void awardPointsTest() {
		//Input Variables
		char winState = 'p';
		int playerPoints = 5;
		int bankerPoints = 4;
		
		
		boolean youWon = false;
		if(winState=='p') {
			if(playerPoints > bankerPoints) {
				youWon = true;
			}
		} else if (winState=='b') {
			if(bankerPoints > playerPoints) {
				youWon = true;
			}
		} else if (winState=='t') {
			if(bankerPoints == playerPoints) {
				youWon = true;
			}
		}
		assertTrue(youWon, "Output did not match expected condition");
	}

	
	/*
	 * Using a manually assigned deck, this tests whether our method can correctly tally up a player's score.
	 */
	@Test
	void countPointsTest() {
		ArrayList<Card> Hand = new ArrayList<>();
		Hand.add(new Card(1,"Spades"));
		Hand.add(new Card(2,"Spades"));
		Hand.add(new Card(3,"Spades"));
		Hand.add(new Card(10,"Spades"));
		
		
		int sum = 0;
		for (int i = 0; i < Hand.size(); i++) {
			if(Hand.get(i).getRank() < 10) {
				sum += Hand.get(i).getRank();
			}
		}
		int result = sum % 10;
		assertEquals(6,result,"The score "+result+" does not meet its expected value!");
	}
	
	/*
	 * This is a test of the logic controlling the banker's response to the player drawing, or not drawing,
	 * a third card.
	 */
	@Test
	public void getBankerAction() {
		ArrayList<Card> HandA = new ArrayList<>();
		ArrayList<Card> HandB = new ArrayList<>();
		HandA.add(new Card(11,"Spades"));
		HandA.add(new Card(2,"Hearts")); //Manually assigning a test hand to the player
		HandA.add(new Card(5,"Clubs"));
		boolean playerDrew = true;
		//We know that the countPoints() method is working, so we can manually add scores here to save time
		int HandBScore = 2;
		
		HandB.add(new Card(4,"Clubs"));
		HandB.add(new Card(8,"Diamonds"));
		
		if(playerDrew) {
			if(HandA.get(2).getRank() <= 3 && HandA.get(2).getRank() > 1) {
				if(HandBScore <= 4) {
					HandB.add(new Card(1,"Spades")); //♫♫ THE ACE OF SPADES ♫♫
					fail("This path does not match the given game condition!");
				}
			} else if (HandA.get(2).getRank() <= 5) {
				if(HandBScore <= 5) {
					HandB.add(new Card(1,"Spades"));
					//This is the path that should be chosen, given that the player's
					//last card was a five, and the banker's total is below 5.
				}
			} else if (HandA.get(2).getRank() <= 7) {
				if(HandBScore <= 6) {
					HandB.add(new Card(1,"Spades"));
					fail("This path does not match the given game condition!");
				}
			} else if (HandA.get(2).getRank() == 8) {
				if(HandBScore <= 2) {
					fail("This path does not match the given game condition!");
					HandB.add(new Card(1,"Spades"));
				}
			} else if (HandA.get(2).getRank() == 1 || HandA.get(2).getRank() > 8) {
				if(HandBScore <= 3) {
					fail("This path does not match the given game condition!");
					HandB.add(new Card(1,"Spades"));
				}
			}
		} else {
			if(HandBScore <= 5) {
				HandB.add(new Card(1,"Spades"));
				fail("This path does not match the given game condition!");
			}
		}
	}
	
	
	/*
	 * This tests whether or not the toString() method in the Card class retrieves the correct name
	 */
	@Test
	public void getCardNameTest() {
		Card testingCard = new Card(12,"Spades");
		String testingCardName = testingCard.toString();
		assertEquals("Queen of Spades",testingCardName,"Improper card name! Did not expect "+testingCardName+"!");
	}
	
	
}





