import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AITest {
	
	private static final int LOOPLIMIT = 100;
	
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Tests the generation of an action when both players have ammo.
	 */
	@Test
	public void testGenerateAction() {
		boolean shoot = false;
		boolean shield = false;
		boolean reload = false;
		for(int i=0; i<LOOPLIMIT; i++){
			AI ai = new AI(1, 1, "Felix");
			if(ai.generateAction(1)==Action.SHOOT){
				shoot = true;
			}
			else if(ai.generateAction(1)==Action.SHIELD){
				shield = true;
			}
			else if(ai.generateAction(1)==Action.RELOAD){
				reload = true;
			}
		}
		assertTrue(shoot);
		assertTrue(shield);
		assertTrue(reload);
	}

	/**
	 * Tests the generation of an action when this player has 0 ammo.
	 * Should never shoot.
	 */
	@Test
	public void testGenerateActionNoAmmo() {
		boolean shoot = false;
		boolean shield = false;
		boolean reload = false;
		for(int i=0; i<LOOPLIMIT; i++){
			AI ai = new AI(0, 1, "Felix");
			if(ai.generateAction(1)==Action.SHOOT){
				shoot = true;
			}
			else if(ai.generateAction(1)==Action.SHIELD){
				shield = true;
			}
			else if(ai.generateAction(1)==Action.RELOAD){
				reload = true;
			}
		}
		assertFalse(shoot);
		assertTrue(shield);
		assertTrue(reload);
	}

	/**
	 * Tests the generation of an action when the opponent has 0 ammo.
	 * Should never shield.
	 */
	@Test
	public void testGenerateActionNoAmmoOpponent() {
		boolean shield = false;
		boolean shoot = false;
		boolean reload = false;
		for(int i=0; i<LOOPLIMIT; i++){
			AI ai = new AI(1, 1, "Felix");
			if(ai.generateAction(0)==Action.SHIELD){
				shield = true;
			}
			else if(ai.generateAction(1)==Action.SHOOT){
				shoot = true;
			}
			else if(ai.generateAction(1)==Action.RELOAD){
				reload = true;
			}
		}
		assertFalse(shield);
		assertTrue(shoot);
		assertTrue(reload);
	}
	
	/**
	 * Tests the generation of an action when both this player and the opponent has 0 ammo.
	 * Should only reload.
	 */
	@Test
	public void testGenerateActionNoAmmoBoth() {
		AI ai = new AI(0, 1, "Felix");
		assertEquals(ai.generateAction(0), Action.RELOAD);
	}
}
