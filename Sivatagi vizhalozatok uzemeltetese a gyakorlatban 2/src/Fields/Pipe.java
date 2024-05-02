package Fields;

import Controll.Controller;
import Enums.Fluid;
import Fields.ActiveFields.ActiveFields;
import Fields.ActiveFields.Pump;
import Players.Player;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for Pipes
 * */
public class Pipe extends Field {

    /**
     * Capacity of the pipe
     */
    private final int capacity;
    /**
     * Time left until the pipe can be broken
     */
    private int breakable = 0;
    /**
     * Time left until the pipe is sticky or slippery
     */
    private int remainingFluidTime = 0;
    /**
     * If true the player can leave the pipe. Is false they can't
     */
    private boolean leave = true;
    /**
     * Fluid state of pipe
     */
    private Fluid fluid = Fluid.DRY;

    /**
     * The ends of the pipe. Default is empty.
     */
    private ArrayList<ActiveFields> fields = new ArrayList<>();

    /**
     * Constructor for Pipe
     * @param capacity Capacity of the pipe
     */
    public Pipe(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Setter for capacity. Only for initialization.
     */
    public void setFields(ArrayList<ActiveFields> fields) {
        this.fields = fields;
    }
    /**
     * Setter for field.
     */
    public void setFields(ActiveFields a){fields.add(a);}
    /**
     * Setter for breakable.
     */
    public void setBreakable(int breakable) {
        this.breakable = breakable;
    }
    /**
     * Setter for remainingFluidTime.
     */
    public void setFluidTime(int remainingFluidTime) {
        this.remainingFluidTime = remainingFluidTime;
    }
    /**
     * Setter for leave.
     */
    public void setLeave(boolean leave) {
        this.leave = leave;
    }
    /**
     * Setter for fluid.
     */
    public void setFluid(Fluid fluid) {
        this.fluid = fluid;
    }
    /**
     * Getter for fields as ActiveFields.
     */
    public ArrayList<ActiveFields> getFields() { return fields; }
    /**
     * Getter for fields as Field.
     */
    @Override
    public ArrayList<Field> getNeighborFields(){ return new ArrayList<Field>(fields);}
    /**
     * Getter for capacity.
     */
    public int getCapacity() { return capacity; }
    /**
     * Getter for breakable.
     */
    public int getBreakable() { return breakable; }
    /**
     * Getter for remainingFluidTime.
     */
    public int getRemainingFluidTime() { return remainingFluidTime; }
    /**
     * Getter for leave.
     */
    public boolean getLeave() { return leave; }
    /**
     * Getter for fluid.
     */
    public Fluid getFluid() { return fluid; }

    /**
      * Method for breaking the pipe.
     * @return True if the pipe is broken
     */
    @Override
    public boolean breakField() {
        if(this.breakable > 0) return false;
        this.setBroken(true);
        return true;
    }

    /**
     * Method for repairing the pipe.
     * @return True if the pipe is repaired
     */
    @Override
    public boolean repair() {
        super.setBroken(false);
        if (Controller.isTest()) {
            breakable = 5;
        }
        else {
            breakable = 3+ new Random().nextInt(8);
        }
        return true;
    }

    /**
     * Method for placing a pump on the pipe.
     * @param newPump The pump to be placed
     * @return True if the pump was placed
     */
    @Override
    public Pipe placePump(Pump newPump) {
    	if(newPump == null) return null;
        ActiveFields oldPump = (ActiveFields) fields.remove(0);

        disconnect(oldPump);

        connect(newPump);

        oldPump.removePipe(this);
        Pipe newPipe;
        Random r = new Random();
        if(Controller.isTest()) {
            newPipe = new Pipe(50);
        }
        else newPipe = new Pipe(30+r.nextInt(41));
        newPipe.connect(newPump);

        newPipe.connect(oldPump);

        newPump.addPipe(this);
        newPump.addPipe(newPipe);

        oldPump.addPipe(newPipe);
        
        newPump.set(newPipe, this);

        return newPipe;
    }


    /**
     * Method for getting the water from the pipe.
     * Prints the amount of water taken.
     * @return The amount of water in the pipe
     */
    @Override
    public int getWater() {
        int w = super.getWaterNoChange();
        super.setWater(0);
        return ((super.isBroken()) || (this.fields.size() < 2)) ? -w : w;
    }

    /**
     * Method for filling the pipe with water.
     * Prints the amount of water returned.
     * @param i The amount of water to be filled in
     * @return The amount of water that was not filled in
     */
    @Override
    public int fillInWater(int i) {
        int waterRightNow = super.getWaterNoChange();
        if(waterRightNow == capacity) return i;
        if (i - (capacity- waterRightNow) > 0) {
            super.setWater(capacity - waterRightNow);
            return i - (capacity-waterRightNow);
        }
        else {
            super.setWater(i);
            return 0;
        }
    }


    /**
     * Method for connecting the pipe to an ActiveField.
     * @param a The ActiveField to be connected to the pipe
     * @return True if the pipe was connected to the ActiveField
     */
    @Override
    public boolean connect(ActiveFields a) {
        fields.add(a);
        return true;
    }

    /**
     * Method for disconnecting the pipe to an ActiveField.
     * @param a The ActiveField to be disconnected to the pipe
     * @return True if the pipe was disconnected to the ActiveField
     */
    @Override
    public boolean disconnect(ActiveFields a) {
        fields.remove(a);
        return true;
    }

    /**
     * Methods for accepting players.
     *
     * @param p The player to be accepted.
     * @return True if the player was accepted.
     */
    @Override
    public Field accept(Player p) {
        if(this.isOccupied())
            return null;
        if(fluid == Fluid.SLIPPERY){
            Random r = new Random();
            int index;
            if (Controller.isTest()) {
                index = 1;
            }
            else {
                index = new Random().nextInt(2);
            }
            fields.get(index).accept(p);
            return fields.get(index);
        }
        else {
            this.setOccupied(true);
            this.setPlayers(p);
        }
        return this;
    }
    /**
     * Methods for removing players.
     *
     * @param p The player to be removed.
     * @return True if the player was removed.
     */
    public boolean removePlayer(Player p){
        if(fluid == Fluid.STICKY){
            if(leave == true){
                leave = false;
                setOccupied(false);
                getPlayers().remove(p);
                return true;
            }
            return false;
        }
        setOccupied(false);
        getPlayers().remove(p);
        return true;
    }
    /**
     * Methods for making the pipe slippery.
     * @return True if the pipe successfully became slippery.
     */
    public boolean makeSlippery(){
        if(fluid == Fluid.STICKY) return false;
        if(remainingFluidTime == 0){
            if (Controller.isTest()) {
                remainingFluidTime = 5;
            }
            else {
                remainingFluidTime = 3+new Random().nextInt(3);
            }
            fluid = Fluid.SLIPPERY;
            return true;
        }
        return false;
    }
    /**
     * Methods for making the pipe sticky.
     * @return True if the pipe successfully became sticky.
     */
    public  boolean makeSticky(){
        if(fluid == Fluid.SLIPPERY) return false;
        if(remainingFluidTime == 0){
            if (Controller.isTest()) {
                remainingFluidTime = 5;
            }
            else {
                remainingFluidTime = 3+new Random().nextInt(3);
            }
            fluid = Fluid.STICKY;
            return true;
        }
        return false;
    }
    /**
     * Method for the game controlled events.
     * Reduces the amount of time left until the pipe becomes dry.
     * If the time is 0 makes the pipe dry.
     */
    public void step(){
        if(breakable > 0){
            breakable--;
        }
        if(remainingFluidTime > 0){
            remainingFluidTime--;
            if(remainingFluidTime == 0){
                fluid = Fluid.DRY;
                leave = true;
            }
        }
    }
    /**
     * Method for getting a string containing all the important information about the pipe.
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

        ArrayList<ActiveFields> fields = this.getFields();
        String fieldsNames ="null";
        for (int i = 0; i < fields.size(); i++) {
            if(i == 0) fieldsNames = "";
            fieldsNames += Controller.objectReverseNames.get(fields.get(i));
            if (i != fields.size() - 1) {
                fieldsNames += ", ";
            }
        }

        return "name: " + Controller.objectReverseNames.get(this)
                + "\noccupied: " + this.isOccupied()
                + "\nwater: " + getWaterNoChange()
                + "\nbroken: " + this.isBroken()
                + "\nplayers: " + playersNames
                + "\nfields: " + fieldsNames
                + "\ncapacity: " + this.getCapacity()
                + "\nbreakable: " + this.getBreakable()
                + "\nrfluidtime: " + this.getRemainingFluidTime()
                + "\nleave: " + this.getLeave()
                + "\nfluid: " + this.getFluid().toString().toLowerCase() + "\n";
    }
}