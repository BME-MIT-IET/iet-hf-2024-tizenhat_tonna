package main.java.Fields.ActiveFields;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.Controll.Controller;
import main.java.Fields.Pipe;

class CisternTest {
	private Cistern cistern;
	private Pipe pipe;
	private Pump pump;
	
	@BeforeEach
	void setUp() throws Exception {
		cistern = new Cistern();
		pipe = new Pipe(10);
		pump = new Pump(20);
	}

	@Test
	void testCreateNewPump_Success() {
        pump = cistern.createNewPump(true);

        assertNotNull(pump);
	}
	
	@Test
	void testCreateNewPump_No_Success() {
        pump = cistern.createNewPump(false);

        assertNull(pump);
	}

	@Test
	void testGetWater() {
		cistern.setWater(10);
        int water = cistern.getWater();

        assertEquals(10, water);
	}

	@Test
	void testPickUpPipe() {
		cistern.step();
		pipe = cistern.pickUpPipe();
		
		assertNotNull(pipe);
        assertEquals(1, cistern.getPipes().size());
        assertTrue(cistern.getPipes().contains(pipe));
	}

	@Test
	void testStep() {
		cistern.addPipe(pipe);
        cistern.step();

        assertEquals(0, cistern.getWater());
        assertNotNull(cistern.pickUpPipe());
        assertNotNull(cistern.getPipes());
	}
	
	@Test
	void testToString() {
		cistern.setBroken(true);
		cistern.setWater(13);
		cistern.addPipe(pipe);
		String cisternString = cistern.toString();

		assertTrue(cisternString.contains("occupied: false"));
		assertTrue(cisternString.contains("water: 13"));
		assertTrue(cisternString.contains("broken: true"));
		assertTrue(cisternString.contains("players: null"));
		assertTrue(cisternString.contains("pipes: null"));
	}

}
