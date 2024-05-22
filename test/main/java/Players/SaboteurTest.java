package main.java.Players;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.enums.Fluid;
import main.java.fields.Pipe;
import main.java.fields.activefields.Pump;
import main.java.players.Saboteur;

class SaboteurTest {
	private Saboteur saboteur;
	private Pipe pipe;
	private Pump pump;

	@BeforeEach
	void setUp() throws Exception {
		saboteur = new Saboteur();
		pipe = new Pipe(10);
		pump = new Pump(30);
	}

	@Test
	void testMakeSlippery() {
		saboteur.setStandingField(pipe);
		pipe.accept(saboteur);
		boolean slippery = saboteur.makeSlippery();

        assertTrue(slippery);
        assertEquals(Fluid.SLIPPERY, pipe.getFluid());
	}
	
	@Test
	void testMakeSticky() {
		saboteur.setStandingField(pipe);
		pipe.accept(saboteur);
		boolean sticky = saboteur.makeSticky();

        assertTrue(sticky);
        assertEquals(Fluid.STICKY, pipe.getFluid());
	}

	@Test
	void testToString() {
		String saboteurString = saboteur.toString();
		
		assertTrue(saboteurString.contains("standingField: null"));
	}
	
	@Test
    void testMove_Success() {
		saboteur.setStandingField(pipe);
		pipe.accept(saboteur);
		pipe.connect(pump);
		pump.addPipe(pipe);

        boolean moved = saboteur.move(pump);

        assertTrue(moved);
        assertEquals(pump, saboteur.getStandingField());
        assertEquals(saboteur, pump.getPlayers().get(0));
    }
	
	@Test
    void testMove_No_Success() {
		saboteur.setStandingField(pump);
		pipe.accept(saboteur);
		pipe.connect(pump);
		pump.addPipe(pipe);
		pipe.setOccupied(true);

        boolean moved = saboteur.move(pipe);

        assertFalse(moved);
        assertEquals(pump, saboteur.getStandingField());
    }
	
	@Test
    void testBreakField() {
		saboteur.setStandingField(pipe);
        boolean broken = saboteur.breakField();

        assertTrue(broken);
        assertTrue(pipe.isBroken());
    }

}
