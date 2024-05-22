package main.java.Fields.ActiveFields;

import main.java.Controll.Controller;
import main.java.Fields.Pipe;
import main.java.Players.Player;
import main.java.StringResource.StringResourceController;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for Cistern
 * */
public class Cistern extends ActiveFields{
    private Random random = new Random();
    /**
     * Last created Pipe. Null if the last pump was just taken.
     */
    private Pipe createdPipe;
    /**
     * Constructor for the cistern.
     */
    public Cistern() {
        createdPipe = null;
        super.setWater(0);
    }

    /**
     * Method for the game controlled events.
     * Gets the water from the pipes and stores it.
     * Creates a new pipe
     */
    @Override
    public void step() {
        if (!getPipes().isEmpty()) {
            for (Pipe pipe : getPipes()) {
                super.setWater(super.getWater() + pipe.getWater());
            }
        }
        if(createdPipe == null){
            if(Controller.isTest()){
                createdPipe = new Pipe(65);
            }
            else createdPipe = new Pipe(30+random.nextInt(41));
        }
    }

    /**
     * Method for creating a new pump.
     * @param b True if the player get a new pump.
     * @return The new pump.
     * */
    @Override
    public Pump createNewPump(boolean b) {
        if(b){
            if(Controller.isTest()){
                return new Pump(100);
            }
            else return new Pump(80+random.nextInt(41));
        }
        else return null;
    }

    /**
     * Method for picking up a (new) pipe from the field.
     * @return The new pipe.
     */
    @Override
    public Pipe pickUpPipe() {
        Controller.waterCounter.addPipe(createdPipe);
        Controller.pipes++;
        Controller.objectNames.put("newPipe"+Controller.pipes, createdPipe);
        Controller.objectReverseNames.put(createdPipe, "newPipe"+Controller.pipes);
        this.addPipe(createdPipe);
        createdPipe.connect(this);
        Pipe tmp = createdPipe;
        createdPipe = null;
        return tmp;
    }
    /**
     * Method for getting a string containing all the important information about the cistern.
     * @return String - returns the important information.
     */
    @Override
    public String toString() {
        ArrayList<Player> players = this.getPlayers();
        String playerBuilder = StringResourceController.stingBuilder(players);

        ArrayList<Pipe> pipes = this.getPipes();
        String pipeBuilder = StringResourceController.stingBuilder(pipes);

          return "name: "+ Controller.objectReverseNames.get(this)
                  + "\noccupied: " + this.isOccupied()
                  + "\nwater: " + getWaterNoChange()
                  + "\nbroken: " + this.isBroken()
                  + "\nplayers: " + playerBuilder
                  + "\npipes: " + pipeBuilder + "\n";
    }
}
