import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 * This class receives information from duel and in this version prints out the relevant information.
 * @author Felix
 *
 */
public class Drawer extends Application{
	//Class fields
	private static Drawer controller = new Drawer();
	private static Random rand = new Random();
	private static Player player1, player2, playerToCreate;
	private static Duel duel;
	private static Sounder sounder;
	private static Stage stage;
	private static Scene startScene, editScene, creatorScene, duelScene;
	private static String[] duelOverText;
	
	//Duel-GUI labels
    @FXML
    private Label printOut, player1Name, player2Name, player1Health, player2Health, player1Ammo, player2Ammo, roundCount;

    //Duel-GUI buttons
    @FXML
    private Button p1ShootButton, p1ShieldButton, p1ReloadButton, p2ShootButton, p2ShieldButton, p2ReloadButton, restartButton, menuButton;

    //Duel-GUI progress bars
    @FXML
    private ProgressBar progressBar, p1HealthBar, p2HealthBar;
    
    //duel-GUI image views
    @FXML
    private ImageView player1Char, player2Char;
    
    //start-GUI buttons
    @FXML
    private Button singlePlayerButton, multiPlayerButton, profilesButton, quit, back, editButton, deleteButton;
	
    //start-GUI Toggle buttons
    @FXML
    private ToggleButton char1, char2, char3, char4, char5, char6;

    //start-GUI text field
    @FXML
    private TextField playerName;
    
    @FXML
    private Label editorProfile;
    
    //start-GUI choice boxes
    @FXML
    private ChoiceBox<Player> chooseProfile, player1Select, player2Select;
    
    //start-GUI list of players
    private static ObservableList<Player> playersToChoose = FXCollections.observableArrayList(new Player(1, 2, "Felix"), new Player(1, 2, "Miguel"));
    
	/**
	 * The constructor, assigns the two player-names to fields
	 * @param player1 The name of the first player
	 * @param player2 The name of the second player
	 */
	public Drawer() {}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Sets up the stage and launches the first scene, the start menu
	 */
	@Override
	public void start(Stage stage) throws Exception {
		for(Player p : playersToChoose){
			p.setPlayerChar(new Image("/images/old_Cowboy.png"));
		}
		Drawer.stage = stage;
		startScene = setupScene(new FXMLLoader(getClass().getResource("start-GUI.fxml")));
		stage.setScene(startScene);
		
		editScene = setupScene(new FXMLLoader(getClass().getResource("editProfiles-GUI.fxml")));
		creatorScene = setupScene(new FXMLLoader(getClass().getResource("createProfile-GUI.fxml")));
		duelScene = setupScene(new FXMLLoader(getClass().getResource("Duel-GUI.fxml")));
		
		controller.player1Select.setValue(playersToChoose.get(0));
		controller.player1Select.setItems(playersToChoose);
		controller.player1Select.setStyle("-fx-font-size: 50");

		controller.player2Select.setValue(playersToChoose.get(1));
		controller.player2Select.setItems(playersToChoose);
		controller.player2Select.setStyle("-fx-font-size: 50");
		
		controller.chooseProfile.setItems(playersToChoose);
		controller.chooseProfile.setValue(playersToChoose.get(0));
		controller.chooseProfile.setStyle("-fx-font-size: 50");

		sounder = new Sounder();
		stage.show();
	}
	
	/**
	 * Creates the duelOverText-array and fills it with different ending-texts.
	 * Also prints out a random duelOver-text with the correct player-names.
	 * @param player1 The winner of the duel
	 * @param player2 The loser of the duel
	 */
	public void setDuelOverText(Player winner, Player loser) {
		duelOverText = new String[] {winner.getName() + " has shot down the scum " + loser.getName() + " and stands victorious!",
				"A message from " + winner.getName() + " to the person playing "  + loser.getName() + ", you seriously couldn't have done better than that?",
				"There is a saying, don't pick a fight agianst a gunslinger with a better hat, " + loser.getName() + " proved that today.",
				"Attention real person playing " + loser.getName() +  ", your opponent is better than you.",
				loser.getName() + " will have to crawl to the nearest doctor, " + winner.getName() + " made sure of that.",
				"Thanks to " + winner.getName() + ", " + loser.getName() + " will be a great feast for the vultures.",
				winner.getName() + " completely and utterly gunslinged " + loser.getName() + " into oblivion.",
				"Hopefully " + loser.getName() + " has health insurance, no thanks to " + winner.getName() + ".",
				winner.getName() + " fled across the desert and " + loser.getName() + " didn't follow because " + loser.getName() + " was dead.",
				loser.getName() + " only wanted to go home, but thanks to " + winner.getName() + " there is a hole in "+ loser.getName() + "'s back."};
		printOut.setText(duelOverText[rand.nextInt(duelOverText.length)]);
	}
	
	/**
	 * Creates a scene from a given FXMLLoader
	 * @param fxmlLoader The loader from a given .fxml-file
	 * @return The new scene that should be set to the stage
	 * @throws Exception
	 */
	public Scene setupScene(FXMLLoader fxmlLoader)throws Exception{
		fxmlLoader.setController(controller);
		Pane pane = (Pane) fxmlLoader.load();
		return new Scene(pane);
	}
	
	/**
	 * Starts a duel 
	 * @throws Exception
	 */
	public void startDuel()throws Exception{
		player1Char.setPreserveRatio(true);
		player2Char.setPreserveRatio(true);
		duel = new Duel(player1, player2, controller);
		Button[] p1Buttons = {p1ShootButton, p1ShieldButton, p1ReloadButton};
		Button[] p2Buttons = {p2ShootButton, p2ShieldButton, p2ReloadButton};
		if(player2 instanceof AI){
	    	for(Button p1Button : p1Buttons){
	    		p1Button.setPrefHeight(70); //216
	    		p1Button.setPrefWidth(220); //680
	    		p1Button.setLayoutX(178); //50
	    		p1Button.setStyle("-fx-font-size: 32;"); //92
	    	}
	    	for(Button p2Button : p2Buttons){
	    		p2Button.setVisible(false);
	    	}
		}
		else{
			for(Button p1Button : p1Buttons){
				p1Button.setPrefHeight(62);
	    		p1Button.setPrefWidth(220);
	    		p1Button.setLayoutX(10);
	    		p1Button.setStyle("-fx-font-size: 30;");
			}
			for(Button p2Button : p2Buttons){
	    		p2Button.setVisible(true);
	    	}
		}
		controller.updateInfo(1);
		
		sounder.playDuelTheme();
		
		stage.setScene(duelScene);
		sounder.playNewRound();
    	duel.startNewRound();
	}
	
	/**
	 * Updates all the labels on the duel-GUI
	 */
	public void updateInfo(int i) {
		player1Name.setText(player1.getName());
		player2Name.setText(player2.getName());
		
		player1Ammo.setText(Integer.toString(player1.getAmmo()));
		player2Ammo.setText(Integer.toString(player2.getAmmo()));
		
		roundCount.setText("Round " + Integer.toString(i));
	}

	//Duel-GUI methods
	
	/**
	 * Prints correct information and plays correct soundFX when both players are reloading
	 */
	public void reloadReload() {
		printOut.setText("Both " + player1.getName() + " and " + player2.getName() + " reloaded!");
		sounder.playReload1();
		sounder.playReload2();
	}
	
	/**
	 * Prints correct information and plays correct soundFX when one player is reloading and the other shielding
	 * @param reloader The player that reloads
	 * @param shielder The player that shields
	 */
	public void reloadShield(Player reloader, Player shielder) {
		printOut.setText(reloader.getName() + " reloaded and " + shielder.getName() + " shielded!");
		sounder.playReload1();
		sounder.playShield2();
	}
	
	/**
	 * Prints correct information and plays correct soundFX when one player is reloading and the other shooting
	 * @param reloader The player that reloads
	 * @param shooter The player that shoots
	 */
	public void reloadShoot(Player reloader, Player shooter) {
		printOut.setText(reloader.getName() + " reloaded and " + shooter.getName() + " shot!");
		
		if(reloader.equals(player1)) {
			p1HealthBar.setProgress(player1.getStock()/2.0);
			sounder.playShot2();
			sounder.playReload1();
		}
		else {
			p2HealthBar.setProgress(player2.getStock()/2.0);
			sounder.playShot1();
			sounder.playReload2();
		}
	}

	/**
	 * Prints correct information and plays correct soundFX when both players are shielding
	 */
	public void shieldShield() {
		printOut.setText("Both " + player1.getName() + " and " + player2.getName() + " shielded!");
		sounder.playShield1();
		sounder.playShield2();
	}
	
	/**
	 * Prints correct information and plays correct soundFX when one player is shielding and the other shooting
	 * @param shielder The player that shields
	 * @param shooter The player that shoots
	 */
	public void shieldShoot(Player shielder, Player shooter) {
		printOut.setText(shielder.getName() + " shielded and " + shooter.getName() + " shot!");
		
		if(shielder.equals(player1)) {
			sounder.playShot2();
			sounder.playShield1();
		}
		else {
			sounder.playShot1();
			sounder.playShield2();
		}
	}
	
	/**
	 * Prints correct information and plays correct soundFX when both players are shooting
	 */
	public void shootShoot() {
		printOut.setText("Both " + player1.getName() + " and " + player2.getName() + " shot!");
		sounder.playShot1();
		sounder.playShot2();
	}
	
	/**
	 * Prints correct information and plays correct SoundFX when one of the gunslingers has 0 stock
	 * @param winner The player that still has stock left
	 * @param loser The player that has 0 stock
	 */
	public void wins(Player winner, Player loser) {
		sounder.duelOver(winner, loser);
		
		if(loser.equals(player1)) {
			player1Char.setImage(new Image(getClass().getResourceAsStream("/images/lost.png")));
		}else {
			player2Char.setImage(new Image(getClass().getResourceAsStream("/images/lost.png")));
		}
		setDuelOverText(winner, loser);
		setDuelFinished(true);
	}
	
	private void setDuelFinished(boolean isFinished){
		p1ShootButton.setVisible(!isFinished);
		p1ShieldButton.setVisible(!isFinished);
		p1ReloadButton.setVisible(!isFinished);
		p2ShootButton.setVisible(!isFinished);
		p2ShieldButton.setVisible(!isFinished);
		p2ReloadButton.setVisible(!isFinished);
		restartButton.setVisible(isFinished);
		menuButton.setVisible(isFinished);
		if(!isFinished){
			printOut.setText("");
			p1HealthBar.setProgress(1.0);
			p2HealthBar.setProgress(1.0);
			try{
				player1.setStock(2);
				player1.setAmmo(1);
	        	player2.setStock(2);
	        	player2.setAmmo(1);
	        	player1Char.setImage(player1.getPlayerChar());
	        	player2Char.setImage(player2.getPlayerChar());
			}
        	catch(NullPointerException e){
        		
        	}
		}
	}
	
	private void noAmmo(Player player) {
		printOut.setText(player.getName() + " has no ammonution!");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		printOut.setText("Choose another option");
	}
	
	/**
	 * Triggered when player1 presses Reload and sets correct action for the player
	 * @param event
	 */
	@FXML
    void player1Reload(ActionEvent event) {
    	player1.setNextAction(Action.RELOAD);
    }

	/**
	 * Triggered when player1 presses Shield and sets correct action for the player
	 * @param event
	 */
    @FXML
    void player1Shield(ActionEvent event) {
    	player1.setNextAction(Action.SHIELD);
    }

    /**
	 * Triggered when player1 presses Shoot and sets correct action for the player
	 * @param event
	 */
    @FXML
    void player1Shoot(ActionEvent event) {
    	if(player1.getAmmo()==0){
			noAmmo(player1);
			return;
    	}
    	player1.setNextAction(Action.SHOOT);
    }

    /**
     * Triggered when player2 presses Reload and sets correct action for the player
     * @param event
     */
    @FXML
    void player2Reload(ActionEvent event) {
    	player2.setNextAction(Action.RELOAD);
    }

    /**
     * Triggered when player2 presses Shield and sets correct action for the player
     * @param event
     */
    @FXML
    void player2Shield(ActionEvent event) {
    	player2.setNextAction(Action.SHIELD);
    }

    /**
     * Triggered when player2 presses Shoot and sets correct action for the player
     * @param event
     */
    @FXML
    void player2Shoot(ActionEvent event) {
    	if(player2.getAmmo()==0){
			noAmmo(player2);
			return;
    	}
    	player2.setNextAction(Action.SHOOT);
    }
    
    /**
     * Restarts the duel with correct player values after the correct button is pressed 
     * @param event
     */
    @FXML
    void restartDuel(ActionEvent event){
    	setDuelFinished(false);
    	try {
			startDuel();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //start-screen methods
    
    @FXML
    void multiPlayerClick(ActionEvent event)throws Exception {
		player1 = player1Select.getValue();
		player2 = player2Select.getValue();
		player1Char.setImage(player1.getPlayerChar());
		player2Char.setImage(player2.getPlayerChar());
		
		startDuel();
    }

    @FXML
    void singlePlayerClick(ActionEvent event)throws Exception {
    	player1 = player1Select.getValue();
    	player2 = new AI(1, 2, "AI");
    	player1Char.setImage(player1.getPlayerChar());
    	player2.setPlayerChar(new Image(getClass().getResourceAsStream("/images/Cloaked_Cowgirl.png")));
    	player2Char.setImage(player2.getPlayerChar());
    	
    	startDuel();
    }
    
    /**
     * Triggered by pressing Back, goes back to the start-screen
     * @param event
     */
    @FXML
    void backClick(ActionEvent event) {
    	setDuelFinished(false);
    	sounder.playMenuTheme();
    	stage.setScene(startScene);
    }

    /**
     * Triggered by pressing Profiles, changes the scene to the edit-Profiles
     * @param event
     */
    @FXML
    void profilesClick(ActionEvent event) {
		stage.setScene(editScene);
    }

    /**
     * Quits the program
     * @param event
     */
    @FXML
    void quitClick(ActionEvent event) {
    	Platform.exit();
    }
    
    //Profile creator methods
    
    @FXML
    void backFromCreator(ActionEvent event) {
    	stage.setScene(editScene);
    }

    /**
     * Chooses character 1 for the player to be created
     * @param event
     */
    @FXML
    void chooseChar1(ActionEvent event) {
    	playerToCreate.setPlayerChar(new Image(getClass().getResourceAsStream("/images/1gorP.png")));
    }

    /**
     * Chooses character 2 for the player to be created
     * @param event
     */
    @FXML
    void chooseChar2(ActionEvent event) {
    	playerToCreate.setPlayerChar(new Image(getClass().getResourceAsStream("/images/Cloaked_Cowgirl.png")));
    }

    /**
     * Chooses character 3 for the player to be created
     * @param event
     */
    @FXML
    void chooseChar3(ActionEvent event) {
    	playerToCreate.setPlayerChar(new Image(getClass().getResourceAsStream("/images/cowboy_concept_procedural_animation_by_grimmdev-d8l0klm.png")));
    }

    /**
     * Chooses character 4 for the player to be created
     * @param event
     */
    @FXML
    void chooseChar4(ActionEvent event) {
    	playerToCreate.setPlayerChar(new Image(getClass().getResourceAsStream("/images/Future-Cowgirl.png")));
    }

    /**
     * Chooses character 5 for the player to be created
     * @param event
     */
    @FXML
    void chooseChar5(ActionEvent event) {
    	playerToCreate.setPlayerChar(new Image(getClass().getResourceAsStream("/images/old_Cowboy.png")));
    }

    /**
     * Chooses character 6 for the player to be created
     * @param event
     */
    @FXML
    void chooseChar6(ActionEvent event) {
    	playerToCreate.setPlayerChar(new Image(getClass().getResourceAsStream("/images/Sneaky_Cowboy.png")));
    }

    //editProfiles-GUI methods
    
    /**
     * Changes scene to createProfile-GUI to create a new player
     * @param event
     */
    @FXML
    void createClick(ActionEvent event) {
    	playerName.setText("Name");
    	editorProfile.setText("Create Profile");
    	setUpCreator(new Player(1, 2));
    }

    /**
     * Deletes a profile from the list of players
     * @param event
     */
    @FXML
    void deleteClick(ActionEvent event) {
    	playersToChoose.remove(chooseProfile.getValue());
    	if(playersToChoose.size() == 0){
    		setButtonsDisabled();
    	}
    	else{
    		chooseProfile.setValue(playersToChoose.get(0));
    		player1Select.setValue(playersToChoose.get(0));
    		if(playersToChoose.size() == 1){
    			player2Select.setValue(playersToChoose.get(0));
    		}
    		else{
    			player2Select.setValue(playersToChoose.get(1));
    		}
    	}
    }
    
    private void setUpCreator(Player player) {
    	//width: 258
    	//height: 202
    	int width = 100; //170
    	
    	ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("/images/1gorP.png")));
    	char1.setGraphic(iv1);
    	
    	ImageView iv2 = new ImageView(new Image(getClass().getResourceAsStream("/images/Cloaked_Cowgirl.png")));
    	char2.setGraphic(iv2);
    	
    	ImageView iv3 = new ImageView(new Image(getClass().getResourceAsStream("/images/cowboy_concept_procedural_animation_by_grimmdev-d8l0klm.png")));
    	char3.setGraphic(iv3);
    	
    	ImageView iv4 = new ImageView(new Image(getClass().getResourceAsStream("/images/Future-Cowgirl.png")));
    	char4.setGraphic(iv4);
    	
    	ImageView iv5 = new ImageView(new Image(getClass().getResourceAsStream("/images/old_Cowboy.png")));
    	char5.setGraphic(iv5);
    	
    	ImageView iv6 = new ImageView(new Image(getClass().getResourceAsStream("/images/Sneaky_Cowboy.png")));
    	char6.setGraphic(iv6);
    	
    	ImageView[] ivs = {iv1, iv2, iv3, iv4, iv5, iv6};
    	for(ImageView iv : ivs){
    		iv.setPreserveRatio(true);
    		iv.setFitHeight(width);
    	}
    	
    	ToggleButton[] buttons = {char1, char2, char3, char4, char5, char6};
    	for(ToggleButton tG : buttons) {
    		tG.setStyle("-fx-base: #ADD74B");
    	}
    	
    	playerToCreate = player;
    	stage.setScene(creatorScene);
    }

    /**
     * Changes the scene to createProfiles-GUI to edit the selected profile
     * @param event
     */
    @FXML
    void editClick(ActionEvent event) {
    	playerName.setText(chooseProfile.getValue().getName());
    	editorProfile.setText("Edit Profile");
    	setUpCreator(chooseProfile.getValue());
    }
    
    /**
     * If the player to be created has been given a name and a selected character, adds the profile to the list
     * Changes scene to editProfiles-GUI
     * @param event
     */
    @FXML
    void doneFromCreator(ActionEvent event) {
    	if(playerName.getText() != null && !playerName.getText().equals("Name") && playerToCreate.getPlayerChar() != null) {
    		playerToCreate.setName(playerName.getText());
    		if(!playersToChoose.contains(playerToCreate)){
    			if(playersToChoose.size() == 0){
        			setButtonsEnabled();
        			chooseProfile.setValue(playerToCreate);
            		player1Select.setValue(playerToCreate);
            		player2Select.setValue(playerToCreate);
        		}
        		else if(playersToChoose.size() == 1){
        			player2Select.setValue(playerToCreate);
        		}
    			playersToChoose.add(playerToCreate);
    		}
    		else{
    			playersToChoose.remove(playerToCreate);
    			playersToChoose.add(playerToCreate);
    		}
    		stage.setScene(editScene);
    	}
    }
    
    /**
     * When the text box in the creator has been clicked, the pre-written text should will be removed
     * @param event
     */
    @FXML
    void profileNameClicked(MouseEvent event) {
    	if(playerName.getText().equals("Name")){
    		playerName.setText("");
    	}
    }

    /**
     * When the text box in the creator has been unclicked, the pre-written text should will be readded if the box is empty
     * @param event
     */
    @FXML
    void profileNameUnclicked(MouseEvent event) {
    	if(playerName.getText().equals("")){
    		playerName.setText("Name");
    	}
    }
    
    //Better name for the two methods below?
    private void setButtonsEnabled(){
    	editButton.setDisable(false);
		deleteButton.setDisable(false);
		singlePlayerButton.setDisable(false);
		multiPlayerButton.setDisable(false);
    }
    
    private void setButtonsDisabled(){
    	editButton.setDisable(true);
		deleteButton.setDisable(true);
		singlePlayerButton.setDisable(true);
		multiPlayerButton.setDisable(true);
    }
    
    /**
     * Starts the timer for each duel
     * @param totalTime The amount of time each round should take
     * @throws Exception
     */
    public void startCountDown(final int totalTime)throws Exception{
    	IntegerProperty timeSeconds = new SimpleIntegerProperty(totalTime*100);
    	Timeline timeline = new Timeline();
    	timeline.getKeyFrames().add(
    			new KeyFrame(Duration.seconds(totalTime),
    			new KeyValue(timeSeconds, 0)));
    	progressBar.progressProperty().bind(timeSeconds.divide(totalTime*100.0));
    	timeline.playFromStart();
    }
}
