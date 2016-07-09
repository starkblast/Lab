package lab;

public class Turn {
	
	boolean gameEnded = true;
	private int turnCounter = (int) (Math.random()* 2); {

		while(!gameEnded)
		{
			if(turnCounter%2 <= 0)
			{
				player1Turn();
				player2Turn();
			}
			if(turnCounter%2 >= 1)
			{            
				player2Turn();
				player1Turn();
			}
			turnCounter++;
		}
		

	}
	
	Object player1Turn() {
		return null;
	}
	
	Object player2Turn() {
		return null;
	}
	
}