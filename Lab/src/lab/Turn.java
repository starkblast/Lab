package lab;

public class Turn {
	
	public boolean onePlaysNext = true;
	public Game game;	 
	
	public void switchPlayer() {
	
		   onePlaysNext = !onePlaysNext;
	
	
	
		 
	
		//Player 1:

	if (onePlaysNext) {
	
			//player 1 acts.
		switchPlayer();
	
	}   
	
		 
	
	//Player 2:
	
	if (!onePlaysNext) {
		
		    //player 2 acts.
	
		switchPlayer();
	
	}
	
	
	}
	
}