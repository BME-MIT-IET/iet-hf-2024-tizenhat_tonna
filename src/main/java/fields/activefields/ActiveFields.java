package main.java.fields.activefields;

import main.java.fields.Field;
import main.java.fields.Pipe;
import main.java.players.Player;

import java.util.ArrayList;

/**
 * Abstract class for active fields.
 * */
public abstract class ActiveFields extends Field {

    /**
     * Pipes connected to the active field. Default is empty.
     */
    private ArrayList<Pipe> pipes  = new ArrayList<>();
    @Override
    public ArrayList<Field> getNeighborFields(){ return new ArrayList<>(pipes);}
    /**
     * Getter for pipes. Only for child classes.
     * @return The pipes connected to the active field
     */
    public ArrayList<Pipe> getPipes() {
        if (pipes.isEmpty()) return new ArrayList<>();
        return pipes;
    }

    /**
     * Setter for pipes. Only for initialization.
     */
    public void setPipes(ArrayList<Pipe> pipes) { //Basic setter if it is needed
        this.pipes = pipes;
    }

    /**
     * Method for adding a pipe to the active field.
     * @param p The pipe to be added
     * @return True if the pipe was added
     */
    @Override
    public boolean addPipe(Pipe p) {
        pipes.add(p);
        return true;
    }

    /**
     * Method for removing a pipe from the active field.
     * @param p The pipe to be removed
     * @return True if the pipe was removed
     */
    @Override
    public boolean removePipe(Pipe p) {
        pipes.remove(p);
        return true;
    }

    /**
     * Method for the game controlled events.
     */
    @Override
    public void step() {
    }
    
    /**
     * Methods for accepting players.
     *
     * @param p The player to be accepted.
     * @return
     */
       @Override
   	public Field accept(Player p) {
           this.setOccupied(false);
           this.setPlayers(p);
       	return this;
   	}

}
