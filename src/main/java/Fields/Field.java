package Fields;

import Fields.ActiveFields.ActiveFields;
import Fields.ActiveFields.Pump;
import Interfaces.Steppable;
import Players.Player;
import java.util.ArrayList;

/**
 * Abstract class for the fields.
 * */
public abstract class Field implements Steppable {

	/**
	 * True if the field is cannot accept more player.
	 * */
	private boolean occupied;

	/**
	 * The amount of water in the field.
	 * */
	private int water;

	/**
	 * True if the field is broken.
	 * */
	private boolean broken;

	/**
	 * Players on the field.
	 * */
	private ArrayList<Player> players = new ArrayList<>();

	/**
	 * Getter for the occupied variable. Only for child classes.
	 * @return occupied variable.
	 * */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * Getter for the broken variable. Only for child classes.
	 * @return broken variable.
	 */
	public boolean isBroken() {
		return broken;
	}

	/**
	 * Getter for the players. Only for child classes.
	 * @return players.
	 */
	public ArrayList<Player> getPlayers() { //Basic getter if it is needed
		/*if (players.size() == 0) {
			return null;
		}*/
		return players;
	}

	public ArrayList<Field> getNeighborFields(){ return new ArrayList<>();}
	/**
	 * Setter for players list.
	 */
	public void setPlayers(Player p) {
		players.add(p);
	}

	/**
	 * Setter for the broken variable.
	 */
	public void setBroken(boolean broken) {
		this.broken = broken;
	}

	/**
	 * Setter for the occupied variable. Only for child classes.
	 * */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	/**
	 * Setter for the water variable. Only for child classes.
	 * */
	public void setWater(int water) {
		this.water = water;
	}

	/**
     * Methods for accepting players.
     *
     * @param p The player to be accepted.
     * @return True if the player was accepted. - always false.
     */
	public Field accept(Player p) {
		return this;
	}


	/**
	 * Method for checking if the field is neighbour of the given field.
	 * @param f The field to be checked.
	 * @return True if the field is neighbour. - always false.
	 * */
	public boolean checkNeighbour(Field f) { //Not used in the skeleton. Probably it will be removed from Proto.
		return false;
	}

	/**
	 * Method for removing a player from the field.
	 * @param p The player to be removed.
	 *          The player must be on the field.
	 * @return True if the player was removed. - always false.
	 * */
	public boolean removePlayer(Player p) {
		setOccupied(false);
		players.remove(p);
		return true;
	}

	/**
	 * Method for breaking the field.
	 * @return True if the field was broken. - always false.
	 * */
	public boolean breakField() {
		return false;
	}

	/**
	 * Method for setting the water flow in the pump.
	 * @param input Pipe - The input pipe of the pump.
	 * @param output Pipe - The output pipe of the pump.
	 * @return True if the water flow was set. - always false.
	 * */
	public boolean set(Pipe input, Pipe output) {
		return false;
	}

	/**
	 * Method for repairing the field.
	 * @return True if the field was repaired. - always false.
	 * */
	public boolean repair() {
		return false;
	}

	/**
	 * Method for placing a pump on the field.
	 * @param p The pump to be placed.
	 * @return True if the pump was placed. - always false.
	 * */
	public Pipe placePump(Pump p) {
		return null;
	}

	/**
	 * Method for creating a new pump.
	 * @param b True if the player get a new pump.
	 * @return The new pump. - always null.
	 * */
	public Pump createNewPump(boolean b) {
		return null;
	}

	/**
	 * Method for getting the water from the field.
	 * Prints the amount of water taken.
	 * @return The amount of water in the field. - always 0.
	 * */
	public int getWater() {
		return water;
	}

	/**
	 * Method for getting the amount of water in field without removing it.
	 * @return water in field.
	 */
	public int getWaterNoChange(){
		return water;
	}

	/**
	 * Method for adding a pipe to the field.
	 * @param p The pipe to be added.
	 * @return True if the pipe was added. - always false.
	 * */
	public boolean addPipe(Pipe p) {
		return false;
	}

	/**
	 * Method for removing a pipe from the field.
	 * @param p The pipe to be removed.
	 * @return True if the pipe was removed. - always false.
	 * */
	public boolean removePipe(Pipe p) {
		return false;
	}

	/**
	 * Method for getting a new pipe from the field.
	 * @return The new pipe. - always null.
	 * */
	public Pipe pickUpPipe() {
		return null;
	}

	/**
	 * Method for filling the field with water.
	 * @param i The amount of water to be filled.
	 * @return The amount of water that was not filled. - always 0.
	 * */
	public int fillInWater(int i) {
		return 0;
	}

	/**
	 * Method for setting the end of the pipe at a pump.
	 * @param p The pump to be set on.
	 * @return True if the end was set. - always false.
	 * */
	public boolean setEnd(Pump p) {
		return false;
	}

	/**
	 * Method for connecting the field to the active fields.
	 * @param a The active fields to be connected.
	 * @return True if the field was connected. - always false.
	 * */
	public boolean connect(ActiveFields a) {
		return false;
	}

	/**
	 * Method for disconnecting the field from the active fields.
	 * @param a The active fields to be disconnected.
	 * @return True if the field was disconnected. - always false.
	 * */
	public boolean disconnect(ActiveFields a) {
		return false;
	}
	/**
	 * Method for making the field slippery.
	 * @return True if the field became slippery. - always false.
	 * */
	public boolean makeSlippery(){
		return false;
	}
	/**
	 * Method for making the field sticky.
	 * @return True if the field became sticky. - always false.
	 * */
	public boolean makeSticky(){
		return false;
	}
}
