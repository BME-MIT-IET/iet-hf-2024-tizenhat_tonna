package Fields.ActiveFields;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Fields.Pipe;
import Players.Mechanic;

class SpringTest {
	private Spring spring;
	private ArrayList<Pipe> pipes;
	private Mechanic mechanic;
	
	@BeforeEach
	void setUp() throws Exception {
		spring = new Spring(10);
		mechanic = new Mechanic();
		pipes = new ArrayList<>();
        pipes.add(new Pipe(30));
        pipes.add(new Pipe(30));
        spring.setPipes(pipes);
	}

	@Test
	void testStep() {
		pipes.get(0).setWater(5);
        pipes.get(1).setWater(10);
        
        spring.step();
       
        assertEquals(0, spring.getWaterOut());
        assertEquals(10, spring.getMaxOutWater());
        assertEquals(-10, pipes.get(0).getWater());
	}

	@Test
	void testToString() {
		pipes.get(0).setWater(20);
        pipes.get(1).setWater(10);
        
        String springString = spring.toString();
        
        assertTrue(springString.contains("occupied: false"));
        assertTrue(springString.contains("water: 0"));
        assertTrue(springString.contains("broken: false"));
        assertTrue(springString.contains("players: null"));
        assertTrue(springString.contains("pipes: null"));
        assertTrue(springString.contains("waterOut: 10"));
        assertTrue(springString.contains("maxOutWater: 10"));
	}
	
	@Test
	void testGetNeighborFields() {
		assertEquals(pipes,spring.getNeighborFields());
	}
	
	@Test
	void testRemovePlayer() {
		mechanic.setStandingField(spring);
		spring.setOccupied(true);
		spring.setPlayers(mechanic);
		spring.accept(mechanic);
		
		boolean removed = spring.removePlayer(mechanic);
		
		assertTrue(removed);
		assertFalse(spring.isOccupied());
	}
}
