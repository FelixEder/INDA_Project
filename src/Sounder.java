import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

/**
 * Sounder is responsible for playing all soundFX in the game when called on by drawer.
 * @author felix
 *
 */
public class Sounder {
	private static AudioClip menuPlayer, duelPlayer, reload1Player, reload2Player, newRoundPlayer, shield1Player, shield2Player, 
	screamPlayer, fanfarPlayer, failfarPlayer, shot1Player, shot2Player;
	
	/**
	 * Constructor for Sounder, takes no parameters and starts playing the menu-theme indefinitely.
	 */
	public Sounder() {
		menuPlayer = new AudioClip(new File("main_menu_theme.wav").toURI().toString());
		duelPlayer = new AudioClip(new File("duelTheme.wav").toURI().toString());
		reload1Player = new AudioClip(new File("reload_gun.wav").toURI().toString());
		reload2Player = new AudioClip(new File("reload_gun2.wav").toURI().toString());
		newRoundPlayer = new AudioClip(new File("new_Round.wav").toURI().toString());
		shield1Player = new AudioClip(new File("shield_Slam.wav").toURI().toString());
		shield2Player = new AudioClip(new File("shield_Slam2.wav").toURI().toString());
		screamPlayer = new AudioClip(new File("scream_of_Pain.wav").toURI().toString());
		fanfarPlayer = new AudioClip(new File("fanfar.wav").toURI().toString());
		failfarPlayer = new AudioClip(new File("failFar.wav").toURI().toString());
		shot1Player = new AudioClip(new File("shot1.wav").toURI().toString());
		shot2Player = new AudioClip(new File("shot2.wav").toURI().toString());
		
		playMenuTheme();
	}
	
	/**
	 * Stops current background-theme and starts playing the menu theme indefinitely.
	 */
	public void playMenuTheme() {
		menuPlayer.stop();
		duelPlayer.stop();
		menuPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		menuPlayer.play();
	}
	
	/**
	 * Stops current background-theme and starts playing the duel theme indefinitely.
	 */
	public void playDuelTheme() {
		duelPlayer.stop();
		menuPlayer.stop();
		duelPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		duelPlayer.play();
	}
	
	/**
	 * Plays the reload1-soundFX
	 */
	public void playReload1() {
		reload1Player.play();
	}
	
	/**
	 * Plays the reload2-soundFX
	 */
	public void playReload2() {
		reload2Player.play();
	}
	
	/**
	 * Plays the newRound-soundFX
	 */
	public void playNewRound() {
		newRoundPlayer.play();
	}
	
	/**
	 * Plays the shield1-soundFX
	 */
	public void playShield1() {
		shield1Player.play();
	}
	
	/**
	 * Plays the shield2-soundFX
	 */
	public void playShield2() {
		shield2Player.play();
	}
	
	/**
	 * Plays the scream-soundFX
	 */
	public void playScream() {
		screamPlayer.play();
	}
	
	/**
	 * Plays the fanfare-soundFX
	 */
	public void playFanfare() {
		fanfarPlayer.play();
	}
	
	/**
	 * Plays the failfare-soundFX
	 */
	public void playFailfare() {
		failfarPlayer.play();
	}
	
	/**
	 * Plays the shot1-soundFX
	 */
	public void playShot1() {
		shot1Player.play();
	}
	
	/**
	 * Plays the shot2-soundFX
	 */
	public void playShot2() {
		shot2Player.play();
	}
	
	/**
	 * When a duel is over the method plays up the correct soundFX.
	 * @param winner The winner of the duel.
	 * @param loser The loser of the duel
	 */
	public void duelOver(Player winner, Player loser) {	
		if(winner instanceof AI) {
			playScream();
			playFailfare();
		}
		else {
			playScream();
			playFanfare();
		}
	}
}