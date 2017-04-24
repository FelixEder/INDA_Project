import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class Duel {

	private Player player1;
	private Player player2;
	private Drawer drawer;
	private int round;
	private boolean gameOver;
	
	/**
	 * Creates a new Duel between two players. 
	 * @param p1 The first player in this duel and the opponent of p2.
	 * @param p2 The second player in this duel and the opponent of p1.
	 */
	public Duel(Player p1, Player p2, Drawer drawer){
		player1 = p1;
		player2 = p2;
		this.drawer = drawer;
		round = 0;
	}
	
	/**
	 * Takes in both actions performed by players and changes relevant stats and tells drawer what to do.
	 */
	public void processActions() {
		int p1Action = player1.getNextAction();
		int p2Action = player2.getNextAction();
		
		if(p1Action == Action.RELOAD) {
			player1.increaseAmmo();
			if(p2Action == Action.RELOAD) {
				player2.increaseAmmo();
				drawer.reloadReload();
			}
			else if(p2Action == Action.SHIELD) {
				drawer.reloadShield(player1, player2);
			}
			else {
				player1.decreaseStock();
				player2.decreaseAmmo();
				drawer.reloadShoot(player1, player2);
			}
		}
		else if(p1Action == Action.SHIELD) {
			if(p2Action == Action.RELOAD) {
				player2.increaseAmmo();
				drawer.reloadShield(player2, player1);
			}
			else if(p2Action == Action.SHIELD) {
				drawer.shieldShield();
			}
			else {
				player2.decreaseAmmo();
				drawer.shieldShoot(player1, player2);
			}
		}
		else {
			player1.decreaseAmmo();
			if(p2Action == Action.RELOAD) {
				player2.decreaseStock();
				player2.increaseAmmo();
				drawer.reloadShoot(player2, player1);
			}
			else if(p2Action == Action.SHIELD) {
				drawer.shieldShoot(player2, player1);
			}
			else {
				player2.decreaseAmmo();
				drawer.shootShoot();
			}
		}
	}
	
	/**
	 * Waits for the player to choose their action.
	 */
	public void waitForInput(int seconds)throws Exception{
		Player[] players = {player1, player2};
		player1.setNextAction(Action.WAIT);
		player2.setNextAction(Action.WAIT);
		
		Thread.sleep(seconds*1000);
		
		for(int i=0; i<players.length; i++){
			if(players[i].getNextAction() == Action.WAIT){
				players[i].setNextAction(players[i].generateAction(players[players.length-1-i].getAmmo()));
			}
		}
	}
	
	/**
	 * Starts this duel.
	 */
	public void startNewRound()throws Exception{
		round++;
		final int countDown = 4;
		if(!gameOver){
			//Thread.sleep(1000);
			drawer.updateInfo(round);
			drawer.startCountDown(countDown);
			Task<Void> waitForInputTask = new Task<Void>(){
	    		@Override
	    		protected Void call()throws Exception{
	    			waitForInput(countDown);
	    			return null;
	    		}
	    	};
	    	waitForInputTask.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
	    		@Override
	    		public void handle(WorkerStateEvent event){
	    			processActions();
	    			if(player1.getStock()==0){
	    				drawer.wins(player2, player1);
	    				gameOver = true;
	    			}
	    			else if(player2.getStock()==0){
	    				drawer.wins(player1, player2);
	    				gameOver = true;
	    			}
	    			try {
						startNewRound();
					} catch (Exception e) {
						e.printStackTrace();
					}
	    		}
	    	});
	    	new Thread(waitForInputTask).start();			
		}
	}
}
