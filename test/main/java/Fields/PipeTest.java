package main.java.Fields;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.control.Controller;
import main.java.enums.Fluid;
import main.java.fields.Field;
import main.java.fields.Pipe;
import main.java.fields.activefields.ActiveFields;
import main.java.fields.activefields.Pump;
import main.java.players.Mechanic;
import main.java.players.Player;

class PipeTest {
	private Pipe pipe;
	private Player player;
	private Pump pump1;
	private Pump pump2;
	
	@BeforeEach
	void setUp() throws Exception {
		pipe = new Pipe(23);
		player = new Mechanic();
		pump1 = new Pump(50);
		pump2 = new Pump(50);
	}
	
	@Test
	void testAccept_Test() {
		Controller.setTest(true);
		pipe.setOccupied(false);
		pipe.setFields(pump1);
		pipe.setFields(pump2);
		pipe.setFluid(Fluid.SLIPPERY);
		
		Field field = pipe.accept(player);

        assertFalse(pipe.isOccupied());
        assertFalse(pipe.getPlayers().contains(player));
        assertNotEquals(pipe, field);
	}
	
	@Test
	void testAccept_Occupied() {
		pipe.setOccupied(true);
        Field field = pipe.accept(player);

        assertTrue(pipe.isOccupied());
        assertFalse(pipe.getPlayers().contains(player));
        assertNotEquals(pipe, field);
	}
	
	@Test
	void testAccept_Fluid() {
		pipe.setOccupied(false);
		pipe.setFields(pump1);
		pipe.setFields(pump2);
		pipe.setFluid(Fluid.SLIPPERY);
		
		Field field = pipe.accept(player);

        assertFalse(pipe.isOccupied());
        assertFalse(pipe.getPlayers().contains(player));
        assertNotEquals(pipe, field);
	}

	@Test
	void testRemovePlayer() {
		pipe.accept(player);

        boolean removed = pipe.removePlayer(player);

        assertFalse(pipe.isOccupied());
        assertTrue(pipe.getPlayers().isEmpty());
        assertTrue(removed);
	}
	
	@Test
	void testRemovePlayer_Sticky_Leave() {
		pipe.setFluid(Fluid.STICKY);
		pipe.accept(player);
		pipe.setLeave(true);

        boolean removed = pipe.removePlayer(player);

        assertFalse(pipe.isOccupied());
        assertTrue(pipe.getPlayers().isEmpty());
        assertTrue(removed);
	}
	
	@Test
	void testRemovePlayer_No_Leave() {
		pipe.setFluid(Fluid.STICKY);
		pipe.accept(player);
		pipe.setLeave(false);

        boolean removed = pipe.removePlayer(player);

        assertTrue(pipe.isOccupied());
        assertFalse(pipe.getPlayers().isEmpty());
        assertFalse(removed);
	}

	@Test
	void testBreakField_Succes() {
		pipe.setBreakable(0);
		boolean broken = pipe.breakField();

        assertTrue(broken);
        assertTrue(pipe.isBroken());
	}
	
	@Test
	void testBreakField_No_Succes() {
		pipe.setBreakable(10);
		boolean broken = pipe.breakField();

        assertFalse(broken);
        assertFalse(pipe.isBroken());
	}
	
	

	@Test
	void testRepair() {
		Controller.setTest(false);
		pipe.breakField();

        boolean repaired = pipe.repair();

        assertTrue(repaired);
        assertFalse(pipe.isBroken());
	}
	
	@Test
	void testRepair_Test() {
		Controller.setTest(true);
		pipe.breakField();

        boolean repaired = pipe.repair();

        assertTrue(repaired);
        assertFalse(pipe.isBroken());
        assertEquals(5,pipe.getBreakable());
	}

	@Test
	void testPlacePump() {
        Pump pump = new Pump(20);
        pipe.setFields(pump1);
        pipe.setFields(pump2);

        Pipe newPipe = pipe.placePump(pump);

        assertNotNull(newPipe);
        assertTrue(pipe.getFields().contains(pump));
        assertTrue(newPipe.getFields().contains(pump));
        assertEquals(2, pump.getPipes().size());
	}
	
	@Test
	void testPlacePump_Null() {
        pipe.setFields(pump1);
        pipe.setFields(pump2);

        Pipe newPipe = pipe.placePump(null);

        assertNull(newPipe);
	}
	
	@Test
	void testPlacePump_Test() {
		Controller.setTest(true);
        Pump pump = new Pump(20);
        pipe.setFields(pump1);
        pipe.setFields(pump2);

        Pipe newPipe = pipe.placePump(pump);

        assertNotNull(newPipe);
        assertTrue(pipe.getFields().contains(pump));
        assertTrue(newPipe.getFields().contains(pump));
        assertEquals(2, pump.getPipes().size());
        assertEquals(50, newPipe.getCapacity());
	}
	
	@Test
	void testGetWater_Success() {
		pipe.fillInWater(20);
		pipe.setBroken(false);
		pipe.connect(pump1);
		pipe.connect(pump2);
		pump1.addPipe(pipe);
		pump2.addPipe(pipe);

        int waterTaken = pipe.getWater();

        assertEquals(20, waterTaken);
        assertEquals(0, pipe.getWaterNoChange());
	}

	@Test
	void testGetWater_Broken() {
		pipe.fillInWater(20);
		pipe.setBroken(true);

        int waterTaken = pipe.getWater();

        assertEquals(-20, waterTaken);
        assertEquals(0, pipe.getWaterNoChange());
	}
	
	@Test
	void testGetWater_Fields() {
		pipe.fillInWater(20);
		pipe.setBroken(false);

        int waterTaken = pipe.getWater();

        assertEquals(-20, waterTaken);
        assertEquals(0, pipe.getWaterNoChange());
	}
	
	@Test
	void testGetWater_Broken_Fields() {
		pipe.fillInWater(20);
		pipe.setBroken(true);
		pipe.connect(pump1);
		pipe.connect(pump2);
		pump1.addPipe(pipe);
		pump2.addPipe(pipe);

        int waterTaken = pipe.getWater();

        assertEquals(-20, waterTaken);
        assertEquals(0, pipe.getWaterNoChange());
	}

	@Test
	void testFillInWater() {
        int initialWater = pipe.getWaterNoChange();

        int remainingWater = pipe.fillInWater(30);

        assertEquals(7, remainingWater);
        assertEquals(0, initialWater);
	}
	
	@Test
	void testFillInWater_Full() {
		pipe.setWater(23);
        int initialWater = pipe.getWaterNoChange();

        int remainingWater = pipe.fillInWater(30);

        assertEquals(30, remainingWater);
        assertEquals(23, initialWater);
	}

	@Test
	void testConnect() {
        boolean connected = pipe.connect(pump1);

        assertTrue(connected);
        assertTrue(pipe.getFields().contains(pump1));
	}

	@Test
	void testDisconnect() {
        pipe.connect(pump2);

        boolean disconnected = pipe.disconnect(pump2);

        assertTrue(disconnected);
        assertFalse(pipe.getFields().contains(pump2));
	}

	@Test
	void testMakeSlippery_Success() {
		boolean slippery = pipe.makeSlippery();

        assertTrue(slippery);
        assertEquals(Fluid.SLIPPERY, pipe.getFluid());
	}
	
	@Test
	void testMakeSlippery_No_Success() {
		pipe.setFluid(Fluid.STICKY);
		boolean slippery = pipe.makeSlippery();

        assertFalse(slippery);
        assertNotEquals(Fluid.SLIPPERY, pipe.getFluid());
	}

	@Test
	void testMakeSticky_Success() {
        boolean sticky = pipe.makeSticky();

        assertTrue(sticky);
        assertEquals(Fluid.STICKY, pipe.getFluid());
	}
	
	@Test
	void testMakeSticky_Success_Test() {
		Controller.setTest(true);
        boolean sticky = pipe.makeSticky();

        assertTrue(sticky);
        assertEquals(Fluid.STICKY, pipe.getFluid());
        assertEquals(5,pipe.getRemainingFluidTime());
	}
	
	@Test
	void testMakeSticky_No_Success() {
		pipe.setFluidTime(10);
        boolean sticky = pipe.makeSticky();

        assertFalse(sticky);
        assertNotEquals(Fluid.STICKY, pipe.getFluid());
	}

	@Test
	void testStep_Breakable() {
		pipe.setBreakable(10);
        int breakable = pipe.getBreakable();
        
        pipe.step();

        assertEquals(breakable - 1, pipe.getBreakable());
	}
	
	@Test
	void testStep_Dry() {
		pipe.setFluid(Fluid.STICKY);
		pipe.setFluidTime(1);
        int fluidTime = pipe.getRemainingFluidTime();
        
        pipe.step();

        assertEquals(fluidTime - 1, pipe.getRemainingFluidTime());
        assertEquals(Fluid.DRY, pipe.getFluid());
        assertTrue(pipe.getLeave());
	}
	
	@Test
	void testStep_Almost_Dry() {
		pipe.setFluid(Fluid.STICKY);
		pipe.setFluidTime(2);
        int fluidTime = pipe.getRemainingFluidTime();
        
        pipe.step();

        assertEquals(fluidTime - 1, pipe.getRemainingFluidTime());
        assertNotEquals(Fluid.DRY, pipe.getFluid());
        assertTrue(pipe.getLeave());
	}

	@Test
	void testToString() {
        pipe.setFields(pump1);
        pipe.setFields(pump2);
        pipe.accept(player);

        String pipeString = pipe.toString();

        assertTrue(pipeString.contains("occupied: true"));
        assertTrue(pipeString.contains("water: 0"));
        assertTrue(pipeString.contains("broken: false"));
        assertTrue(pipeString.contains("players: " + Controller.objectReverseNames.get(player)));
        assertTrue(pipeString.contains("fields: " + Controller.objectReverseNames.get(pump1) + ", " + Controller.objectReverseNames.get(pump2)));
        assertTrue(pipeString.contains("capacity: 23"));
        assertTrue(pipeString.contains("breakable: " + pipe.getBreakable()));
        assertTrue(pipeString.contains("rfluidtime: " + pipe.getRemainingFluidTime()));
        assertTrue(pipeString.contains("leave: true"));
        assertTrue(pipeString.contains("fluid: dry"));
	}

	@Test
	void testGetNeighborFields() {
		ArrayList<ActiveFields> pumps = new ArrayList<>();
		pumps.add(pump1);
		pumps.add(pump2);
		
		pipe.setFields(pumps);
		
		assertEquals(pumps,pipe.getNeighborFields());
	}
}
