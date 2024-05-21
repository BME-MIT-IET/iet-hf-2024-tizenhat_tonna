package Players;

import Controll.Controller;


/**
 * Class for the saboteur player.
 * */
public class Saboteur extends Player {

	/**
	 * Constructor for the saboteur.
	 */
	public Saboteur() {
	}

	/**
	 * Method for making standingField slippery
	 * @return boolean - returns true if action was successful.
	 */
	public boolean makeSlippery(){
		boolean result = this.getStandingField().makeSlippery();
		return result;
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
