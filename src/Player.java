import java.util.Random;

import javafx.scene.image.Image;

/**
 * A class that stores information for the various players used in the game.
 * @author Felix
 *
 */
public class Player {
	//Fields
	private int ammo;
	private int stock;
	private String name;
	private int previousAction;
	private int nextAction;
	private int wins;
	private Image playerChar;
	
	protected Random rng;
	
	/**
	 * Creates a new player object
	 * @param ammo The amount of bullets at start, can't be lower than 0.
	 * @param name The name of the player
	 * @param stock The amount of health the player starts with, can't be lower than 0.
	 */
	public Player(int ammo, int stock, String name) {
		setValues(ammo, stock);
		this.name = name;
	}
	
	/**
	 * Second constructor in order to create nameless Persons
	 * @param ammo The amount of bullets at start, can't be lower than 0.
	 * @param stock The amount of health the player starts with, can't be lower than 0.
	 */
	public Player(int ammo, int stock) {
		setValues(ammo, stock);
	}
	
	private void setValues(int ammo, int stock){
		if(ammo < 0 || stock < 0) {
			throw new IllegalArgumentException();
		}
		this.ammo = ammo;
		this.stock = stock;
		rng = new Random();
		int[] actions = {Action.SHOOT, Action.SHIELD, Action.RELOAD};
		previousAction = actions[rng.nextInt(3)];
	}
	
	//Methods
	/**
	 * @return	 the amounts of bullet the player has.
	 */
	public int getAmmo() {
		return ammo;
	}
	
	/**
	 * @return name of the player
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Increases the number of bullets the player has by one.
	 */
	public void increaseAmmo() {
		ammo++;
	}
	
	/**
	 * Decreases the number of bullets the player has by one.
	 * If the player currently has 0 bullets, ammo will not be decreased.
	 */
	public void decreaseAmmo() {
		if(ammo != 0) {
			ammo--;
		}
	}
	
	/**
	 * @return the amount of stock the player has.
	 */
	public int getStock() {
		return stock;
	}
	
	/**
	 * Decreases the number of stock the player has by one.
	 * If the player currently has no stock, stock will not be decreased.
	 */
	public void decreaseStock() {
		if(stock != 0) {
			stock--;
		}
	}
	
	public void setStock(int stock){
		if(stock < 1) {
			throw new IllegalArgumentException();
		}
		this.stock = stock;
	}
	
	public void setAmmo(int ammo){
		if(ammo < 0) {
			throw new IllegalArgumentException();
		}
		this.ammo = ammo;
	}

	public int getNextAction() {
		return nextAction;
	}

	public void setNextAction(int nextAction) {
		if(this.nextAction != Action.WAIT && nextAction == Action.WAIT){
			previousAction = this.nextAction;
		}
		this.nextAction = nextAction;
	}
	
	public int generateAction(int opponentAmmo){
		if(ammo == 0 && previousAction == Action.SHOOT){
			int[] actions = {Action.RELOAD, Action.SHIELD};
			return actions[rng.nextInt(2)];
		}
		return previousAction;
	}

	public int getWins() {
		return wins;
	}

	public void incrementWins() {
		wins++;
	}

	public Image getPlayerChar() {
		return playerChar;
	}

	public void setPlayerChar(Image playerChar) {
		this.playerChar = playerChar;
	}
	
	@Override
	public String toString(){
		return name;
	}
}