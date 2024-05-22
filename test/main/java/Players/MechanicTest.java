package main.java.Players;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.Fields.Pipe;
import main.java.Fields.ActiveFields.Cistern;
import main.java.Fields.ActiveFields.Pump;

class MechanicTest {
	private Mechanic mechanic;
	private Pump pump;
	private Pipe pipe;
	private Cistern cistern;

	@BeforeEach
	void setUp() throws Exception {
		mechanic = new Mechanic();
		pump = new Pump(10);
		pipe = new Pipe(30);
		cistern = new Cistern();
	}

	@Test
	void testRepair() {
		pump.setBroken(true);
		mechanic.setStandingField(pump);
		
		boolean repaired = mechanic.repair();
		
		assertTrue(repaired);
        assertFalse(pump.isBroken());
	}

	@Test
	void testPlacePump() {
		mechanic.setStandingField(pipe);
		mechanic.setHoldingPump(pump);
		pump.addPipe(pipe);
		pipe.connect(pump);
		
		Pipe newPipe = mechanic.placePump();
		
		assertNull(mechanic.getHoldingPump());
		assertNotNull(newPipe);
	}

	@Test
	void testDisconnect() {
		mechanic.setStandingField(pump);
        pump.addPipe(pipe);
        pipe.connect(pump);
        
        boolean disconnect= mechanic.disconnect(pipe);
        
        assertTrue(disconnect);
        assertEquals(pipe,mechanic.getHoldingPipe());
        
	}

	@Test
	void testConnect() {
		mechanic.setStandingField(pump);
        mechanic.setHoldingPipe(pipe);

        boolean connect = mechanic.connect();

        assertTrue(connect);
        assertNull(mechanic.getHoldingPipe());
	}

	@Test
	void testGetPump() {
		mechanic.setStandingField(cistern);
		Pump newPump = mechanic.getPump();
		
		assertNotNull(newPump);
        assertEquals(newPump,mechanic.getHoldingPump());
	}

	@Test
	void testPickUpPipe() {
		mechanic.setStandingField(cistern);
		cistern.step();
		
		boolean pickedUp = mechanic.pickUpPipe();
		
		assertTrue(pickedUp);
        assertNotNull(mechanic.getHoldingPipe());
	}

	@Test
	void testToString() {
		String mechanicString = mechanic.toString();
		
		assertTrue(mechanicString.contains("standingField: null"));
        assertTrue(mechanicString.contains("holdingPipe: null"));
        assertTrue(mechanicString.contains("holdingPump: null"));
	}
	
    @Test
    void testSet() {
    	mechanic.setStandingField(pump);
        Pipe input = new Pipe(10);
        Pipe output = new Pipe(14);
        pump.addPipe(input);
        pump.addPipe(output);
        input.connect(pump);
        output.connect(pump);

        boolean set = mechanic.set(input, output);

        assertTrue(set);
    }

}
