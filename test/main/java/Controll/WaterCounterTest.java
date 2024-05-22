package main.java.Controll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.Fields.Pipe;
import main.java.Fields.ActiveFields.Cistern;

class WaterCounterTest {
	private WaterCounter waterCounter;
	private Cistern cistern;
	private Pipe pipe;

	@BeforeEach
	void setUp() throws Exception {
		waterCounter = new WaterCounter();
		cistern = new Cistern();
		pipe = new Pipe(10);
		
        waterCounter.addPipe(pipe);
        waterCounter.addCistern(cistern);
	}

	@Test
	void testReset() {
        waterCounter.reset();
        
        assertEquals(0, waterCounter.getSaboteur());
        assertEquals(0, waterCounter.getMechanic());
        assertFalse(waterCounter.getEnd());
        assertTrue(waterCounter.getCisterns().isEmpty());
        assertTrue(waterCounter.getPipes().isEmpty());
	}

	@Test
	void testAddCistern() {
        assertTrue(waterCounter.getCisterns().contains(cistern));
	}

	@Test
	void testAddPipe() {
        assertTrue(waterCounter.getPipes().contains(pipe));
	}

	@Test
	void testSetEnd() {
		waterCounter.setEnd();

        assertTrue(waterCounter.getEnd());
	}

	@Test
	void testCount_End() {
		pipe.fillInWater(-6);
		cistern.setWater(10);
        waterCounter.setEnd();
        
        waterCounter.count();

        assertEquals(0, waterCounter.getSaboteur());
        assertEquals(10, waterCounter.getMechanic());
	}
	
	@Test
	void testCount_No_End() {
		pipe.fillInWater(6);
		cistern.setWater(10);
        
        waterCounter.count();

        assertEquals(6, waterCounter.getSaboteur());
        assertEquals(0, waterCounter.getMechanic());
        
        waterCounter.setEnd();
        
        waterCounter.count();
        assertEquals(10, waterCounter.getMechanic());
	}

	@Test
	void testToString() {
        waterCounter.count();

        String exp = "saboteur: 0\nmechanic: 0\n";
        assertEquals(exp, waterCounter.toString());
	}

	@Test
	void testWinner_Draw() {
		waterCounter.setEnd();
        waterCounter.count();

        assertEquals("Döntetlen", waterCounter.winner());
	}
	
	@Test
	void testWinner_Mechanic() {
		pipe.fillInWater(6);
		cistern.setWater(10);
		
        waterCounter.setEnd();
        waterCounter.count();
        
        assertEquals("Mechanic", waterCounter.winner());   
	}
	
	@Test
	void testWinner_Mechanic_Saboteur() {
		pipe.fillInWater(10);
		cistern.setWater(6);
        
        waterCounter.setEnd();
        waterCounter.count();
        
        assertEquals("Saboteur", waterCounter.winner());   
	}

}
