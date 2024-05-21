package Fields.ActiveFields;

import Controll.Controller;
import Fields.Pipe;
import Players.Player;

import java.util.ArrayList;

/**
 * Class for Spring
 * */
public class Spring extends ActiveFields{

    /**
     * The amount of water that the spring gives out. Default value is maxOutWater.
     */
    int waterOut;
    /**
     * The maximum amount of water that the spring can give out.
     */
    int maxOutWater;

    /**
     * Constructor for Spring.
     * @param maxOutWater The maximum amount of water that the spring can give out.
     */
    public Spring(int maxOutWater) {
        this.waterOut = maxOutWater;
        this.maxOutWater = maxOutWater;
    }
    /**
     * Getter for waterOut.
     * @return waterOut - returns waterOut.
     */
    public int getWaterOut() { return waterOut; }
    /**
     * Getter for maxOutWater.
     * @return maxOutWater - returns maxOutWater.
     */
    public int getMaxOutWater() { return maxOutWater; }

    /**
     * Method for the game controlled events.
     * Give the water to the pipes.
     */
    @Override
    public void step() {
        waterOut = maxOutWater;
        for(int i = 0; i < this.getPipes().size(); i++){
            waterOut = getPipes().get(i).fillInWater(waterOut);
            if(waterOut <= 0){
                break;
            }
        }
    }
    /**
     * Method for getting a string containing all the important information about the spring.
     * @return String - returns the important information.
     */
    @Override
    public String toString() {
        ArrayList<Player> players = this.getPlayers();

        String playersNames = "null";

        for (int i = 0; i < players.size(); i++) {
            if(i == 0) playersNames = "";
            playersNames += Controller.objectReverseNames.get(players.get(i));
            if (i != players.size() - 1) {
                playersNames += ", ";
            }
        }

        ArrayList<Pipe> pipes = this.getPipes();
        String pipesNames ="null";
        for (int i = 0; i < pipes.size(); i++) {
            if(i == 0) pipesNames = "";
            pipesNames += Controller.objectReverseNames.get(pipes.get(i));
            if (i != pipes.size() - 1) {
                pipesNames += ", ";
            }
        }
        return "name: "+ Controller.objectReverseNames.get(this)
                + "\noccupied: " + this.isOccupied()
                + "\nwater: " + getWaterNoChange()
                + "\nbroken: " + this.isBroken()
                + "\nplayers: " + playersNames
                + "\npipes: " + pipesNames
                + "\nwaterOut: " + this.getWaterOut()
                + "\nmaxOutWater: " + this.getMaxOutWater() + "\n";
    }
}