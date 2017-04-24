import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


public class PlayerTest {
	Player testPlayer;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testPlayer = new Player(1, 1, "Roland");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlayer() {
		Player consTest = new Player(2, 1, "Eddie");
		assertTrue(consTest.getAmmo() == 2);
		assertTrue(consTest.getName().equals("Eddie"));
	
		consTest = new Player(0, 1, "Susannah");
		assertTrue(consTest.getAmmo() == 0);
		assertTrue(consTest.getName().equals("Susannah"));
		try {
			consTest = new Player(-1, 1, "Jake");
			fail();
		}
		catch(IllegalArgumentException e) {}
		try {
			consTest = new Player(1, -1, "Jake");
			fail();
		}
		catch(IllegalArgumentException e) {}
	}

	@Test
	public void testIncreaseAmmo() {
		testPlayer.increaseAmmo();
		assertTrue(testPlayer.getAmmo() == 2);
	}

	@Test
	public void testDecreaseAmmo() {
		testPlayer.decreaseAmmo();
		assertTrue(testPlayer.getAmmo() == 0);
		testPlayer.decreaseAmmo();
		assertTrue(testPlayer.getAmmo() == 0);
	}
	
	@Test
	public void testDecreaseStock() {
		testPlayer.decreaseStock();
		assertTrue(testPlayer.getStock() == 0);
		testPlayer.decreaseStock();
		assertTrue(testPlayer.getStock() == 0);
	}
}
