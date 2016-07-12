package lab;

public class Turn {
	
	public boolean onePlaysNext = true;
	public Game game;


	public Turn(Game game) {
		this.game= game;
	}
	
	public void switchPlayer() {
		   onePlaysNext = !onePlaysNext;
		   
		// sides light up
		if (onePlaysNext) {
			game.player1 = "#8AAFAF";
			game.player2 = "#8C4646";
			game.hasmoved = false;
			
		}   
		
		
		if (!onePlaysNext) {
			game.player2 = "#AF7979";
			game.player1 = "#588C7E";
			game.hasmoved = false;
		}
	}
}