package main.java.fields.activefields;

import main.java.control.Controller;
import main.java.fields.Pipe;
import main.java.players.Player;
import main.java.stringresource.StringResourceController;

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
        String playerBuilder = StringResourceController.stringBuilder(players);


        ArrayList<Pipe> pipes = this.getPipes();
        String pipeBuilder = StringResourceController.stringBuilder(pipes);

        return "name: "+ Controller.objectReverseNames.get(this)
                + "\noccupied: " + this.isOccupied()
                + "\nwater: " + getWaterNoChange()
                + "\nbroken: " + this.isBroken()
                + "\nplayers: " + playerBuilder
                + "\npipes: " + pipeBuilder
                + "\nwaterOut: " + this.getWaterOut()
                + "\nmaxOutWater: " + this.getMaxOutWater() + "\n";
    }
}