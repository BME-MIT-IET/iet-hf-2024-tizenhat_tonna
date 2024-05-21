package Players;

import Controll.Controller;
import Fields.ActiveFields.ActiveFields;
import Fields.ActiveFields.Pump;
import Fields.Pipe;


/**
 * Class for the mechanic player.
 * */
public class Mechanic extends Player {

    /**
     *The pump that the mechanic is holding. Default is null.
     */
    private Pump holdingPump;
    /**
     *The pipe that the mechanic is holding. Default is null.
     */
    private Pipe holdingPipe;

    /**
     * Constructor for the mechanic.
     *  holdingPipe = null;
     *  holdingPump = null;
     */
    public Mechanic() {
        this.holdingPipe = null;
        this.holdingPump = null;
    }

    /**
     * Setter for the holdingPump.
     * @param holdingPump The pump that the mechanic is holding.
     */
    public void setHoldingPump(Pump holdingPump) {
        this.holdingPump = holdingPump;
    }
    /**
     * Getter for the holdingPump.
     * @return holdingPump
     */
    public Pump getHoldingPump() { return holdingPump; }

    /**
     * Setter for the holdingPipe.
     * @param holdingPipe The pipe that the mechanic is holding.
     */
    public void setHoldingPipe(Pipe holdingPipe) { //Basic setter for the holdingPipe.
        this.holdingPipe = holdingPipe;
    }
    /**
     * Getter for the holdingPipe.
     * @return holdingPipe
     */
    public Pipe getHoldingPipe() { return holdingPipe; }
    /**
     *Method for repairing the field where the player is standing.
     * @return boolean - returns true if the field is repaired.
     */
    @Override
    public boolean repair() {
        return getStandingField().repair();
    }

    /**
     *Method for placing a pump on the field where the player is standing.
     * @return Pipe - The other (new) half of the pipe.
     */
    @Override
    public Pipe placePump() {
        Pipe p = getStandingField().placePump(holdingPump);
        if(p != null) holdingPump = null;
        return p;
    }

    /**
     * Method for disconnecting a pipe from a pump.
     * @param p Pipe
     *          The pipe that will be disconnected.
     * @return boolean - returns true if the pipe is disconnected.
     */
    @Override
    public boolean disconnect(Pipe p) {
        boolean result = super.getStandingField().removePipe(p);
        boolean result2 = false;
		if(result) {
			result2 = p.disconnect((ActiveFields) super.getStandingField());
			if(result2) holdingPipe = p;
		}
		return result && result2;
    }
    
    /**
     * Method for connecting the holdingPipe to a pump.
     * @return boolean - returns true if the pipe is connected.
     */
    @Override
    public boolean connect() {
        if(holdingPipe == null) return false;
        boolean result = super.getStandingField().addPipe(holdingPipe);
		if(!result) return false;
        boolean b = holdingPipe.connect((ActiveFields)super.getStandingField());
        if(b) holdingPipe = null;
        return b;
    }

    /**
     * Method for getting a pump from the field where the player is standing.
     * @return Pump - The new pump.
     */
    @Override
    public Pump getPump() {
        boolean b = false;
        if(holdingPump == null) b = true;
        holdingPump = getStandingField().createNewPump(b);
        return holdingPump; // mechanic can get a new pump
    }

    /**
     * Method for picking up a new pipe from the field where the player is standing.
     * @return boolean - returns true if the pipe is picked up.
     */
    @Override
    public boolean pickUpPipe() {
        holdingPipe = super.getStandingField().pickUpPipe();
        return holdingPipe != null;
    }
    /**
     * Method for getting a string containing all the important information about the mechanic.
     * @return String - returns the important information.
     */
    @Override
    public String toString() {
        return "name: "+ Controller.objectReverseNames.get(this)
                + "\nstandingField: " + Controller.objectReverseNames.get(this.getStandingField())
                + "\nholdingPipe: " + Controller.objectReverseNames.get(this.getHoldingPipe())
                + "\nholdingPump: " + Controller.objectReverseNames.get(this.getHoldingPump()) + "\n";
    }
}