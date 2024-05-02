package Controll;

import Drawing.*;
import Enums.Fluid;
import Fields.ActiveFields.ActiveFields;
import Fields.ActiveFields.Cistern;
import Fields.ActiveFields.Pump;
import Fields.ActiveFields.Spring;
import Fields.Field;
import Fields.Pipe;
import Interfaces.Steppable;
import Players.Mechanic;
import Players.Player;
import Players.Saboteur;

import javax.swing.text.View;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

@SuppressWarnings("DuplicatedCode")
public class Controller {
    /**
     * If true the game is random
     * */
    private static boolean random = true;
    /**
     * Getter for random
     * */
    public static boolean isRandom() {return random; }

    /**
     * Contains the names of the objects, the keys are the objects.
     */
    public static HashMap<String, Object> objectNames = new HashMap<>();

    /**
     * Contains the objects, the keys are the names of the objects.
     */
    public static HashMap<Object, String> objectReverseNames = new HashMap<>();
    /**
     * WaterCounter of the game
     * */
    public static WaterCounter waterCounter = new WaterCounter();
    /**
     * True if the game is tested
     * */
    private static boolean test = false;
    /**
     * Getter for test
     * */
    public static boolean isTest() {return test;}
    /**
     * Name of the used file
     * */
    private static String fileName="";
    /**
     * Path of the used file
     * */
    private static String filePath="";

    /**
     * List of the test result
     * */
    private static final ArrayList<String> outResults = new ArrayList<>();

    /**
    * List of the activePlayers
    * */
    private static final ArrayList<Player> activePlayers = new ArrayList<>();
  
    /**
    * Current Player
    * */
    private static Player currentPlayer;
  
    /**
     * List of the commands
     * */
    public static ArrayList<String> commandList = new ArrayList<>();

    /**
     * Number of new pumps with commands
     */
    public static int pipes=0;
    /**
     * Number of new pipes with commands
     */
    public static int pumps=0;

    /**
     * True after the command "create", you cannot create new objects after this
     * Resets after the restart command
     */
    public static boolean gameMode = false;

    public static int moves = 0;
    public static boolean lastResult = true;
    private static int turncount = 0;
    public static String getActivePlayerName(){
        return objectReverseNames.get(currentPlayer);
    }

    public static ArrayList<Player> getAllPlayers() { return activePlayers; }

    public static void SetActivePlayer(Player p) { currentPlayer = p; activePlayers.remove(0); activePlayers.add(currentPlayer); }

    public static Player GetActivePlayer() { return currentPlayer; }

    public static boolean getLastResult() { return lastResult;}
    public static boolean changeActivePlayer(){
        currentPlayer = activePlayers.get(0); // az első játékos a sor végére rakom, jelenleg ő az aktív
        activePlayers.remove(0);
        activePlayers.add(currentPlayer);
        turncount++;
        if(turncount == activePlayers.size()){
            turncount = 0;
            return true;
        }
        else return false;
    }
    /**
     *Function for controlling the game.
     *Reads a command than calls a function to execute it.
     * */
    public static void Run() throws FileNotFoundException {
        while(commandList.size() != 0) {
            String command = commandList.get(0);
            commandList.remove(0);
            String[] cmd = command.split(" ");
            switch(cmd[0]) {
                case("load"): load(cmd[1]); break;
                case("pipe"): pipe(cmd); break;
                case("pump"): pump(cmd); break;
                case("cistern"): cistern(cmd); break;
                case("spring"): spring(cmd); break;
                case("mechanic"): mechanic(cmd); break;
                case("saboteur"): saboteur(cmd); break;
                case("connectpipe"): connectpipe(cmd); break;
                case("random"): random(cmd); break;
                case("create"): create(); break;
                case("show"): show(cmd); break;
                case("showobject"): showobject(cmd); break;
                case("move"): move(cmd); break;
                case("breakfield"): breakfield(cmd); break;
                case("repair"): repair(cmd); break;
                case("placepump"): placepump(cmd); break;
                case("set"): set(cmd); break;
                case("disconnect"): disconnect(cmd); break;
                case("connect"): connect(cmd); break;
                case("getpump"): getpump(cmd); break;
                case("pickuppipe"): pickuppipe(cmd); break;
                case("makesticky"): makesticky(cmd); break;
                case("makeslippery"): makeslippery(cmd); break;
                case("save"): save(cmd); break;
                case("testall"): testAll(cmd); break;
                case("list"): list(); break;
                case("addplayer"): addplayer(cmd); break;
                case("step"): step(cmd); break;
                case("endturn"): endturn(cmd); break;
                case("count"): count(cmd); break;
                case("test"): test(cmd); break;
                case("setend"): setend(cmd); break;
                case("setpump"): setpump(cmd); break;
                case("restart"): restart(cmd); break;
                case("exit"): return;
                default: System.out.println("Hibás parancs.");
            }
        }
    }

    /**
     * Game mode after "create" you cannot create new objects in this mode.
     * @throws FileNotFoundException
     */
    public static void Game() throws FileNotFoundException {
        while(gameMode) {
            currentPlayer = activePlayers.get(0); // az első játékos a sor végére rakom, jelenleg ő az aktív
            activePlayers.remove(0);
            activePlayers.add(currentPlayer);
            Scanner stdInScanner = new Scanner(System.in);
            if (commandList.size() == 0){
                commandList.add(stdInScanner.nextLine());
            }
            String command = commandList.get(0);
            commandList.remove(0);
            String[] cmd = command.split(" ");
            switch(cmd[0]) {
                case("show"): show(cmd); break;
                case("showobject"): showobject(cmd); break;
                case("move"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else move(cmd); break;
                case("breakfield"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else breakfield(cmd); break;
                case("repair"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else repair(cmd); break;
                case("placepump"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else placepump(cmd); break;
                case("set"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else set(cmd); break;
                case("disconnect"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else disconnect(cmd); break;
                case("connect"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else connect(cmd); break;
                case("getpump"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else getpump(cmd); break;
                case("pickuppipe"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!");else pickuppipe(cmd); break;
                case("makesticky"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else makesticky(cmd); break;
                case("makeslippery"): moves++; if (!((Player)objectNames.get(cmd[1])).equals(currentPlayer)) System.out.println("Nem te vagy a soron következő játékos!"); else makeslippery(cmd); break;
                case("save"): save(cmd); break;
                case("testall"): testAll(cmd); break;
                case("list"): list(); break;
                case("addplayer"): addplayer(cmd); break;
                case("step"): step(cmd); break;
                case("endturn"): endturn(cmd); break;
                case("count"): count(cmd); break;
                case("test"): test(cmd); break;
                case("setend"): setend(cmd); break;
                case("setpump"): setpump(cmd); break;
                case("restart"): restart(cmd); break;
                case("exit"): return;
                default: System.out.println("Hibás parancs.");
            }
            if (moves == activePlayers.size()) {
                moves = 0;
                endturn(cmd);
            }
        }
        Run();
    }
    /**
     * Function for loading a file.
     * */
    public static void load(String cmd){
        try {
            outResults.clear();
            System.out.println(cmd);
            Scanner scanner = new Scanner(new File(cmd));
            filePath = cmd;
            String separator = "\\";
            String[] tmp=cmd.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
            fileName = tmp[tmp.length-1];
            while (scanner.hasNextLine()){
                commandList.add(scanner.nextLine());
                System.out.println(commandList.get(commandList.size()-1));
            }
            if (test) {
                commandList.add("save " + filePath.replace(".in", ".out"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nem található a fájl!");
        }
    }

    public static void loadFileFromSrcToReader(String fileNameToOpen) {
        // a text file is located in src folder in the project
        Path rootDir = Paths.get(".").normalize().toAbsolutePath();
        File file = new File(rootDir.toString() + "/" + fileNameToOpen);
            try {
                Reader input = new FileReader(file);
                // Checks if reader is ready
                BufferedReader br = new BufferedReader(input);
                String line = "";

                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                // Closes the reader
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

            /**
             * Function for creating a pump.
             * */
    public static void pump(String[] cmd){
        Pump tmp = new Pump(Integer.parseInt(cmd[2]));
        String[][] commands = new String[cmd.length-3][2];
        for(int i=3; i<cmd.length; i++){
            commands[i-3] = cmd[i].split(":");
        }

        for(int i=0; i<commands.length; i++){
            switch (commands[i][0]){
                case "water": tmp.setWater(Integer.parseInt(commands[i][1])); break;
                case "broken": tmp.setBroken(Boolean.parseBoolean(commands[i][1])); break;
                case "draw" : PumpDraw pd = new PumpDraw(Integer.parseInt(commands[i][1]), Integer.parseInt(commands[i][2]));
                    ViewGame.setDrawsNames(pd, tmp); ViewGame.setDrawsReverseNames(tmp, pd); break;
            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
    }
    /**
     * Function for creating a pipe.
     * */
    public static void pipe(String[] cmd){
        Pipe tmp = new Pipe(Integer.parseInt(cmd[2]));
        String[][] commands = new String[cmd.length-3][2];
        for(int i=3; i<cmd.length; i++){
            commands[i-3] = cmd[i].split(":");
        }
        for(int i=0; i<commands.length; i++){
            switch (commands[i][0]){
                case "fluid":
                    switch (commands[i][1]){
                        case "dry": tmp.setFluid(Fluid.DRY); break;
                        case "sticky": tmp.setFluid(Fluid.STICKY); break;
                        case "slippery": tmp.setFluid(Fluid.SLIPPERY); break;
                    }
                    break;
                case "rfluidtime": tmp.setFluidTime(Integer.parseInt(commands[i][1])); break;
                case "breakable": tmp.setBreakable(Integer.parseInt(commands[i][1])); break;
                case "broken": tmp.setBroken(Boolean.parseBoolean(commands[i][1])); break;
                case "water": tmp.setWater(Integer.parseInt(commands[i][1])); break;
                case "leave": tmp.setLeave(Boolean.parseBoolean(commands[i][1])); break;
                case "draw" : PipeDraw pd = new PipeDraw(Integer.parseInt(commands[i][1]), Integer.parseInt(commands[i][2]), Integer.parseInt(commands[i][3]), Integer.parseInt(commands[i][4]));
                ViewGame.setDrawsNames(pd, tmp); ViewGame.setDrawsReverseNames(tmp, pd); break;

            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        waterCounter.addPipe(tmp);
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
    }
    /**
     * Function for creating a cistern.
     * */
    public static void cistern(String[] cmd){
        Cistern tmp = new Cistern();
        String[][] commands = new String[cmd.length-2][2];
        for(int i=2; i<cmd.length; i++){
            commands[i-2] = cmd[i].split(":");
        }
        for(int i=0; i<commands.length; i++) {
            switch (commands[i][0]) {
                case "water": tmp.setWater(Integer.parseInt(commands[0][1])); break;
                case "draw" : CisternDraw cd = new CisternDraw(Integer.parseInt(commands[i][1]), Integer.parseInt(commands[i][2]));
                    ViewGame.setDrawsNames(cd, tmp); ViewGame.setDrawsReverseNames(tmp, cd); break;
            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        waterCounter.addCistern(tmp);
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
    }
    /**
     * Function for creating a spring.
     * */
    public static void spring(String[] cmd){
        Spring tmp = new Spring(Integer.parseInt(cmd[2]));
        String[][] commands = new String[cmd.length-2][2];
        for(int i=2; i<cmd.length; i++){
            commands[i-2] = cmd[i].split(":");
        }
        for(int i=0; i<commands.length; i++) {
            switch (commands[i][0]) {
                case "draw" : SpringDraw sd = new SpringDraw(Integer.parseInt(commands[i][1]), Integer.parseInt(commands[i][2]));
                    ViewGame.setDrawsNames(sd, tmp); ViewGame.setDrawsReverseNames(tmp, sd); break;
            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
    }
    /**
     * Function for creating a saboteur.
     * */
    public static void saboteur(String[] cmd){
        Saboteur tmp = new Saboteur();
        Field f = (Field)objectNames.get(cmd[2]);
        tmp.setStandingField(f);
        SaboteurDraw sd = new SaboteurDraw(0,0); ViewGame.setDrawsNames(sd, tmp); ViewGame.setDrawsReverseNames(tmp, sd);
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
        activePlayers.add(tmp);
    }
    /**
     * Function for creating a mechanic.
     * */
    public static void mechanic(String[] cmd){
        Mechanic tmp = new Mechanic();
        Field f = (Field)objectNames.get(cmd[2]);
        tmp.setStandingField(f);
        MechanicDraw sd = new MechanicDraw(0,0); ViewGame.setDrawsNames(sd, tmp); ViewGame.setDrawsReverseNames(tmp, sd);
        String[][] commands = new String[cmd.length-3][2];
        for(int i=3; i<cmd.length; i++){
            commands[i-3] = cmd[i].split(":");
        }
        for(int i=0; i<commands.length; i++) {
            switch (commands[i][0]) {
                case "pump":
                    tmp.setHoldingPump((Pump)objectNames.get(commands[i][1]));
                    break;
                case "pipe":
                    tmp.setHoldingPipe((Pipe)objectNames.get(commands[i][1]));
                    break;
            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
        activePlayers.add(tmp);
    }
    /**
     * Function for connecting a pipe to an active field.
     * */
    public static void connectpipe(String[] cmd){
        Pipe pipe = (Pipe)objectNames.get(cmd[1]);
        ActiveFields activeField = (ActiveFields)objectNames.get(cmd[2]);
        pipe.setFields(activeField);
        activeField.addPipe(pipe);
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
    }
    /**
     * Function for switching random off and on.
     * */
    public static void random(String[] cmd){
        if (test) {
            if(cmd.length == 2){
                switch (cmd[1]){
                    case "false": random = false;
                        outResults.add("A véletlen események ki lettek kapcsolva."); break;
                    case "true": random = true;
                        outResults.add("A véletlen események be lettek kapcsolva."); break;
                }
            }else {
                random=true;
                outResults.add("A véletlen események be lettek kapcsolva.");
            }
        }
        else {
            if(cmd.length == 2){
                switch (cmd[1]){
                    case "false": random = false;
                        System.out.println("A véletlen események ki lettek kapcsolva."); break;
                    case "true": random = true;
                        System.out.println("A véletlen események be lettek kapcsolva."); break;
                }
            }else {
                random=true;
                System.out.println("A véletlen események be lettek kapcsolva.");
            }
        }

    }
    /**
     * Function for starting the game.
     * */
    public static void create() {
        objectNames.put("wc", waterCounter);
        objectReverseNames.put(waterCounter, "wc");
        if (test) outResults.add("A pálya létrehozása sikeresen lezajlott. Kezdődhet a játék!");
        else System.out.println("A pálya létrehozása sikeresen lezajlott. Kezdődhet a játék!");
        if(!test) gameMode = true;
    }
    /**
     * Function for showing where a player stands.
     * */
    public static void show(String[] cmd){
        Player p = (Player)objectNames.get(cmd[1]);
        String[] commands = cmd[2].split(":");
        switch (commands[1]){
            case "player":
                if (test) outResults.add(p.toString());
                else System.out.println(p);
                break;
            case "field":
                if (test) outResults.add(objectReverseNames.get(p.getStandingField()).toString());
                else System.out.println(objectReverseNames.get(p.getStandingField()));
                break;
        }
    }
    /**
     * Function for displaying important information about the object.
     * */
    public static void showobject(String[] cmd){
        Object object = objectNames.get(cmd[1]);
        if (test) outResults.add(object.toString());
        else System.out.println(object.toString());
    }
    /**
     * Function for moving a player to a field.
     * */
    public static void move(String[] cmd){
        Player p = (Player)objectNames.get(cmd[1]);
        Field f = (Field)objectNames.get(cmd[2]);
        ArrayList<Field> neighbors = p.getStandingField().getNeighborFields();
        if(neighbors.contains(f)) {
            if (p.move(f)) {
                if (test) {outResults.add("Sikeres művelet");lastResult = true;}
                else {System.out.println("Sikeres művelet");lastResult = true;}
            } else {
                if (test) {outResults.add("Sikertelen művelet");lastResult = false;}
                else {
                    System.out.println("Sikertelen művelet");
                    lastResult = false;
                    moves--;
                }
            }
        }
        else{
            if (test) {outResults.add("Sikertelen művelet");lastResult = false;}
            else {
                System.out.println("Sikertelen művelet");
                lastResult = false;
                moves--;
            }
        }
    }
    /**
     * Function for breaking a field by a player.
     * */
    public static void breakfield(String[] cmd){
        Player p = (Player)objectNames.get(cmd[1]);
        if(p.breakField()){
            if (test) {outResults.add("Sikeres művelet"); lastResult = true;}
            else {System.out.println("Sikeres művelet");lastResult = true;}
        }else  {
            if (test) {outResults.add("Sikertelen művelet"); lastResult = false;}
            else {
                System.out.println("Sikertelen művelet");
                lastResult = false;
                moves--;
            }
        }
    }
    /**
     * Function for repairing a field by a player.
     * */
    public static void repair(String[] cmd){
        Player p = (Player)objectNames.get(cmd[1]);
        if(p.repair()){
            lastResult = true;
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            lastResult = false;
            if (test) outResults.add("Sikertelen művelet");
            else {
                System.out.println("Sikertelen művelet");
                moves--;
            }
        }
    }
    /**
     * Function for placing a pump by a player.
     * */
    public static void placepump(String[] cmd){
    	//   |Pump A|===new=Pipe===|new Pump|===old=Pipe===|Pump B|
        Player p = (Player)objectNames.get(cmd[1]); //player
        ActiveFields pumpA, pumpB;
        pumpA = pumpB = null;
        try {
        	pumpA = ((Pipe)p.getStandingField()).getFields().get(0);
        	pumpB = ((Pipe)p.getStandingField()).getFields().get(1);
        } catch(Exception e) {
        	
        }
    	
        Pump hp = ((Mechanic)p).getHoldingPump(); //new pump
        Pipe pipe = p.placePump(); //new pipe
        if(pipe != null ){
            pipes++;
            String s = "newPipe"+pipes;
            objectNames.put(s, pipe);
            objectReverseNames.put(pipe, s);
            waterCounter.addPipe(pipe);
            lastResult = true;
            
            //elements according to drawing
            Pipe oldPipe = hp.getPipes().get(0);
            
            //___notes:___
            //the new pump is at: mecD.getX()+25, mecD.getY()+25
            //Drawables fo thoose who have
            PipeDraw oldPipeD = (PipeDraw)ViewGame.objectDrawReverseNames.get(oldPipe);
            Drawable pumpAD = ViewGame.objectDrawReverseNames.get(pumpA);
            Drawable pumpBD = ViewGame.objectDrawReverseNames.get(pumpB);
            
            //drawing the new pump
            MechanicDraw mecD = (MechanicDraw)ViewGame.objectDrawReverseNames.get(p);
            PumpDraw newPumpD = new PumpDraw(mecD.getX(), mecD.getY());
            ViewGame.setDrawsNames(newPumpD, hp); 
            ViewGame.setDrawsReverseNames(hp, newPumpD);
            //drawing the new pipe
            PipeDraw newPipeD = new PipeDraw(0,0,0,0);
            newPipeD.setCoords(newPumpD, pumpAD);
            ViewGame.setDrawsNames(newPipeD, pipe); 
            ViewGame.setDrawsReverseNames(pipe, newPipeD);
            
            //redrawing the old pipe
            oldPipeD.setCoords(newPumpD, pumpBD);
            
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            lastResult = false;
            if (test) outResults.add("Sikertelen művelet");
            else {
                System.out.println("Sikertelen művelet");
                moves--;
            }
        }
    }
    /**
     * Function for setting a pump by a player.
     * */
    public static void set(String[] cmd){
         Player player = (Player)objectNames.get(cmd[1]);
         if(player.getStandingField().set((Pipe)objectNames.get(cmd[2]), (Pipe)objectNames.get(cmd[3]))){
             lastResult = true;
             if (test) outResults.add("Sikeres művelet");
             else System.out.println("Sikeres művelet");
         }else  {
             lastResult = false;
             if (test) outResults.add("Sikertelen művelet");
             else {
                 System.out.println("Sikertelen művelet");
                 moves--;
             }
         }
    }
    /**
     * Function for disconnecting a pipe by a player.
     * */
    public static void disconnect(String[] cmd){
        Player player = (Player)objectNames.get(cmd[1]);
        if(player.disconnect((Pipe)objectNames.get(cmd[2]))){
            lastResult = true;
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            lastResult = false;
            if (test) outResults.add("Sikertelen művelet");
            else {
                System.out.println("Sikertelen művelet");
                moves--;
            }
        }
    }
    /**
     * Function for connecting a pipe by a player.
     * */
    public static void connect(String[] cmd){
        Player player = (Player)objectNames.get(cmd[1]);
        
        Pipe holdedPipe = null;
        Pump standing = null;
        try {
        	holdedPipe = ((Mechanic)player).getHoldingPipe();
        	standing = (Pump)player.getStandingField();
        } catch(Exception e) {}
        if(player.connect()){
            lastResult = true;
            
            PipeDraw pd = (PipeDraw)ViewGame.objectDrawReverseNames.get(holdedPipe);
            Drawable toPumpD = ViewGame.objectDrawReverseNames.get(holdedPipe.getFields().get(0));
            Drawable fromPumpD = ViewGame.objectDrawReverseNames.get(standing);
            pd.setCoords(fromPumpD, toPumpD);
            
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            lastResult = false;
            if (test) outResults.add("Sikertelen művelet");
            else {
                System.out.println("Sikertelen művelet");
                moves--;
            }
        }
    }
    /**
     * Function for getting a pump by a player.
     * */
    public static void getpump(String[] cmd){
        Player p = (Player)objectNames.get(cmd[1]);
        Pump pump = p.getPump();
        if(pump != null ){
            pumps++;
            String s = "newPump"+pumps;
            objectNames.put(s, pump);
            objectReverseNames.put(pump, s);
            lastResult = true;
        
            
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            lastResult = false;
            if (test) outResults.add("Sikertelen művelet");
            else {
                System.out.println("Sikertelen művelet");
                moves--;
            }
        }
    }
    /**
     * Function picking up a pipe by a player.
     * */
    public static void pickuppipe(String[] cmd){
        Player player = (Player)objectNames.get(cmd[1]);
        if(player.pickUpPipe()){
            lastResult = lastResult && true;
            
            //legyen már az új csőnek drawable-je is
            Pipe newPipe = ((Mechanic)player).getHoldingPipe();
            PipeDraw newPipeD = new PipeDraw(-100,0,0,0);
            ViewGame.setDrawsNames(newPipeD, newPipe); 
            ViewGame.setDrawsReverseNames(newPipe, newPipeD);
            
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            lastResult = false;
            if (test) outResults.add("Sikertelen művelet");
            else {
                System.out.println("Sikertelen művelet");
                moves--;
            }
        }
    }
    /**
     * Function for making a pipe sticky by a player.
     * */
    public static void makesticky(String[] cmd){
        Player player = (Player)objectNames.get(cmd[1]);
        if(player.makeSticky()){
            lastResult = true;
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            lastResult = false;
            if (test) outResults.add("Sikertelen művelet");
            else {
                System.out.println("Sikertelen művelet");
                moves--;
            }
        }
    }
    /**
     * Function for making a pipe slippery by a player.
     * */
    public static void makeslippery(String[] cmd){
        Player player = (Player)objectNames.get(cmd[1]);
        System.out.println(cmd[1]);
        if(player.makeSlippery()){
            lastResult = true;
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            lastResult = false;
            if (test) outResults.add("Sikertelen művelet");
            else {
                System.out.println("Sikertelen művelet");
                moves--;
            }
        }
    }
    /**
     * Function for saving the results of a test.
     * The fuction compares the output of the game to the expected output.
     * The result of the comparison is shown on the console.
     * */
    public static void save(String[] cmd) {
        try (PrintWriter out = new PrintWriter(cmd[1].replace(".in", ".out"))) {
            for (int i = 0; i < outResults.size(); i++) {
                out.println(outResults.get(i));
            }
        }
        catch(FileNotFoundException e) {
           
        }
        try {
            Scanner scannerResult = new Scanner(new File(cmd[1]));
            Scanner scannerExpected = new Scanner((new File(cmd[1].replace(".out", ".test"))));
            ArrayList<String> result = new ArrayList<>();
            ArrayList<String> expected = new ArrayList<>();
            while (scannerResult.hasNextLine()){
                result.add(scannerResult.nextLine().strip());
            }
            while (scannerExpected.hasNextLine()){
                expected.add(scannerExpected.nextLine().strip());
            }
            String separator = "\\";
            String[] tmp=cmd[1].replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
            fileName = tmp[tmp.length-1];
            System.out.println("Test name: " + fileName.replace(".out", ""));
            if (result.size() != expected.size()) {
                System.out.println("Test failed. The 2 files do not have the same amount of lines.");
                return;
            }
            int errors = 0;
            if (result.size() > 0 && expected.size() > 0) {
                for (int i = 0; i < expected.size(); i++) {
                    if (!result.get(i).equals(expected.get(i))) {
                        System.out.println("Error in line " + (i+1) + ".\nExpected: " + expected.get(i) + ", but got: " + result.get(i));
                        errors++;
                    }
                }
            }
            if (errors == 0 && result.size() > 0 && expected.size() > 0) {
                System.out.println("Test succeeded.\n");
            }
            else {
                System.out.println("Test failed.\n");
            }
            pipes=pumps=0;
            waterCounter.reset();
            objectNames.clear();
            objectReverseNames.clear();
        }
        catch(FileNotFoundException e) {
            
        }
        outResults.clear();
    }
    /**
     * Function for doing all the tests.
     * */
    public static void testAll(String[] cmd) {
        try {
            Scanner scanner = new Scanner(new File(cmd[1] + "\\Alltests.txt"));
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                load(cmd[1] + "\\" + line);
            }
        } catch (FileNotFoundException e) {
           
          
        }
    }
    /**
     * Function for listing all objects in the game.
     * */
    public static void list(){
        for (Object obj : objectNames.values()) {
            System.out.print(objectReverseNames.get(obj) + " ");
        }        System.out.println();
    }
    /**
     * Function for putting a player on a field.
     * */
    public static void addplayer(String[] cmd) {
        Field f = (Field) objectNames.get(cmd[1]);
        Player p = (Player) objectNames.get(cmd[2]);
        if(f.accept(p) != null) {
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            if (test) outResults.add("Sikertelen művelet");
            else System.out.println("Sikertelen művelet");
        }
    }

    public static void step(String[] cmd){
        Steppable s = (Steppable)objectNames.get(cmd[1]);
        s.step();
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
    }
    /**
     * Function for ending a turn.
     * Responsible for calling the step function for all steppable objects.
     * */
    public static void endturn(String[] cmd){
        //elvégzi a kör végével járó lépéseket (vízszámolás, objektumok step függvényének hívása stb…)
        //vízszámlálás
        //water counter lehet hogy üres
        waterCounter.count();
        //léptetés
         for (Object obj : objectNames.values()) {
            if(obj instanceof Steppable) {
                Steppable value = (Steppable)obj;
                value.step();
            }
        }
        System.out.println("Sikeres művelet");
    }
    /**
     * Function for countig the points for the two sides.
     * */
    public static void count(String[] cmd){
        waterCounter.count();
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
    }
    /**
     * Function for restaring the game.
     * */
    public static void restart(String[] cmd){
        random = true;
        objectNames.clear();
        objectReverseNames.clear();
        waterCounter.reset();
        test = false;
        pumps=pipes=0;
        System.out.println("Sikeres művelet");
        if(!test) gameMode = false;
    }
    /**
     * Function for putting the game into test mode.
     * */
    public static void test(String[] cmd){
        if(cmd[1].equals("true")) test=true;
        else if(cmd[1].equals("false")) test=false;
        System.out.println("Sikeres művelet");
    }
    /**
     * Function for signaling to the watercounter that the game ended.
     * */
    public static void setend(String[] cmd){
        waterCounter.setEnd();
        if (test) outResults.add("Sikeres művelet");
        else System.out.println("Sikeres művelet");
    }
    /**
     * Function for setting the in/out pipes of a pump.
     * */
    public static void setpump(String[] cmd){
        Pump pump = (Pump)objectNames.get(cmd[1]);
        if(pump.set((Pipe)objectNames.get(cmd[2]), (Pipe)objectNames.get(cmd[3]))){
            if (test) outResults.add("Sikeres művelet");
            else System.out.println("Sikeres művelet");
        }else  {
            if (test) outResults.add("Sikertelen művelet");
            else System.out.println("Sikertelen művelet");
        }
    }
}
