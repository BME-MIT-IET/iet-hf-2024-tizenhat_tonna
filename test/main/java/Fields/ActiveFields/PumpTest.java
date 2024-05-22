package main.java.Fields.ActiveFields;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.Fields.Pipe;

class PumpTest {
	private Pump pump;
    private ArrayList<Pipe> pipes;

	@BeforeEach
	void setUp() throws Exception {
		pump = new Pump(100);
        pipes = new ArrayList<>();
        pipes.add(new Pipe(50));
        pipes.add(new Pipe(50));
        pump.setPipes(pipes);
	}

	@Test
	void testSet() {
		Pipe inputPipe = pipes.get(0);
        Pipe outputPipe = pipes.get(1);
        
        boolean result = pump.set(inputPipe, outputPipe);
        assertTrue(result);
        assertEquals(0, pump.getWaterFrom());
        assertEquals(1, pump.getWaterTo());
	}

	@Test
	void testRepair() {
		boolean repairResult = pump.repair();
		
		assertFalse(pump.isBroken());
        assertTrue(repairResult);
	}

	@Test
	void testStep() {
		pump.setWaterFrom(0);
		pump.setWaterTo(1);
		pump.step();
		
        assertEquals(0,pump.getWater());
        assertEquals(0,pipes.get(pump.getWaterTo()).getWater());
	}

	@Test
	void testToString() {
		pump.setOccupied(true);
		pump.setWater(10);
		pump.setBroken(true);
		pump.setWaterFrom(0);
		pump.setWaterTo(1);
		String pumpString = pump.toString();
		
		assertTrue(pumpString.contains("occupied: true"));
        assertTrue(pumpString.contains("water: 10"));
        assertTrue(pumpString.contains("broken: true"));
        assertTrue(pumpString.contains("players: null"));
        assertTrue(pumpString.contains("pipes:"));
        assertTrue(pumpString.contains("tank: 100"));
        assertTrue(pumpString.contains("waterFrom: null"));
        assertTrue(pumpString.contains("waterTo: null"));
	}

}
