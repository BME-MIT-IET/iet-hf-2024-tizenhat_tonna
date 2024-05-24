package main.java.players;

import main.java.control.Controller;


/**
 * Class for the saboteur player.
 * */
public class Saboteur extends Player {

	/**
	 * Constructor for the saboteur.
	 */
	public Saboteur() {
		//Nothing can be inicialized here
	}

	/**
	 * Method for making standingField slippery
	 * @return boolean - returns true if action was successful.
	 */
	@Override
	public boolean makeSlippery(){
		return this.getStandingField().makeSlippery();

	}
	/**
	 * Method for getting a string containing all the important information about the saboteur.
	 * @return String - returns the important information.
	 */
	@Override
	public String toString() {
		return "name: "+ Controller.objectReverseNames.get(this)
				+ "\nstandingField: " + Controller.objectReverseNames.get(this.getStandingField()) + "\n";
	}
}
