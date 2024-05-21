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
import StringResource.StringResourceController;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


@SuppressWarnings("DuplicatedCode")
public class Controller {
    private Controller() {}
    static Logger logger = Logger.getLogger(Controller.class.getName());
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
    public static final Map<String, Object> objectNames = new HashMap<>();

    /**
     * Contains the objects, the keys are the names of the objects.
     */
    public static final Map<Object, String> objectReverseNames = new HashMap<>();
    /**
     * WaterCounter of the game
     * */
    public static final WaterCounter waterCounter = new WaterCounter();
    /**
     * True if the game is tested
     * */
    private static boolean test = false;
    /**
     * Getter for test
     * */
    public static boolean isTest() {return test;}

    public static void setTest(boolean value){ test = value;}
    
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
    private static  Player currentPlayer;
  
    /**
     * List of the commands
     * */
    public static final List<String> commandList = new ArrayList<>();

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

    public static List<Player> getAllPlayers() { return activePlayers; }

    public static void setActivePlayer(Player p) { currentPlayer = p; activePlayers.remove(0); activePlayers.add(currentPlayer); }

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
    public static void Run() {
        while(!commandList.isEmpty()) {
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
                default: logger.log(Level.INFO, "Hibás parancs.");
            }
        }
    }

    /**
     * Game mode after "create" you cannot create new objects in this mode.
     */
    public static void Game(){
        while(gameMode) {
            currentPlayer = activePlayers.get(0); // az első játékos a sor végére rakom, jelenleg ő az aktív
            activePlayers.remove(0);
            activePlayers.add(currentPlayer);
            Scanner stdInScanner = new Scanner(System.in);
            if (commandList.isEmpty()){
                commandList.add(stdInScanner.nextLine());
            }
            String command = commandList.get(0);
            commandList.remove(0);
            String[] cmd = command.split(" ");
            switch(cmd[0]) {
                case("show"): show(cmd); break;
                case("showobject"): showobject(cmd); break;
                case("move"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else move(cmd); break;
                case("breakfield"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else breakfield(cmd); break;
                case("repair"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else repair(cmd); break;
                case("placepump"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else placepump(cmd); break;
                case("set"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else set(cmd); break;
                case("disconnect"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else disconnect(cmd); break;
                case("connect"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else connect(cmd); break;
                case("getpump"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else getpump(cmd); break;
                case("pickuppipe"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER);else pickuppipe(cmd); break;
                case("makesticky"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else makesticky(cmd); break;
                case("makeslippery"): moves++; if (!objectNames.get(cmd[1]).equals(currentPlayer)) logger.log(Level.INFO, StringResourceController.WRONG_PLAYER); else makeslippery(cmd); break;
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
                default: logger.log(Level.INFO, "Hibás parancs.");
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
        try(Scanner scanner = new Scanner(new File(cmd))) {
            outResults.clear();
            System.out.println(cmd);
            String rootDirectory = System.getProperty("user.dir");
            String modifiedPath=cmd.replace("/", File.separator).replace("\\", File.separator);
            Scanner scanner = new Scanner(new File(rootDirectory+File.separator+"Sivatagi vizhalozatok uzemeltetese a gyakorlatban 2"+ File.separator+modifiedPath));
            filePath = modifiedPath;
            String separator = "\\";
            String[] tmp=cmd.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
            fileName = tmp[tmp.length-1];
            while (scanner.hasNextLine()){
                commandList.add(scanner.nextLine());
                logger.log(Level.INFO, ()-> commandList.get(commandList.size()-1));
            }
            if (test) {
                commandList.add("save " + filePath.replace(".in", ".out"));
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "Nem található a fájl!");
        }
    }

    public static void loadFileFromSrcToReader(String fileNameToOpen) {
        // a text file is located in src folder in the project
        Path rootDir = Paths.get(".").normalize().toAbsolutePath();
        File file = new File(rootDir.toString() + "/" + fileNameToOpen);
        try( Reader input = new FileReader(file)) {
            try (BufferedReader br = new BufferedReader(input)) {

                // Checks if reader is ready

                String line = "";

                while ((line = br.readLine()) != null) {
                    logger.log(Level.INFO, line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, StringResourceController.FILE_NOT_FOUND);
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

        for (String[] command : commands) {
            switch (command[0]) {
                case StringResourceController.WATER:
                    tmp.setWater(Integer.parseInt(command[1]));
                    break;
                case "broken":
                    tmp.setBroken(Boolean.parseBoolean(command[1]));
                    break;
                case "draw":
                    PumpDraw pd = new PumpDraw(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    ViewGame.setDrawsNames(pd, tmp);
                    ViewGame.setDrawsReverseNames(tmp, pd);
                    break;
                default:
                    break;
            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
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
        for (String[] command : commands) {
            switch (command[0]) {
                case "fluid":
                    switch (command[1]) {
                        case "dry":
                            tmp.setFluid(Fluid.DRY);
                            break;
                        case "sticky":
                            tmp.setFluid(Fluid.STICKY);
                            break;
                        case "slippery":
                            tmp.setFluid(Fluid.SLIPPERY);
                            break;
                        default:
                            break;
                    }
                    break;
                case "rfluidtime":
                    tmp.setFluidTime(Integer.parseInt(command[1]));
                    break;
                case "breakable":
                    tmp.setBreakable(Integer.parseInt(command[1]));
                    break;
                case "broken":
                    tmp.setBroken(Boolean.parseBoolean(command[1]));
                    break;
                case StringResourceController.WATER:
                    tmp.setWater(Integer.parseInt(command[1]));
                    break;
                case "leave":
                    tmp.setLeave(Boolean.parseBoolean(command[1]));
                    break;
                case "draw":
                    PipeDraw pd = new PipeDraw(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[4]));
                    ViewGame.setDrawsNames(pd, tmp);
                    ViewGame.setDrawsReverseNames(tmp, pd);
                    break;
                default:
                    break;

            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        waterCounter.addPipe(tmp);
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
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
        for (String[] command : commands) {
            switch (command[0]) {
                case StringResourceController.WATER:
                    tmp.setWater(Integer.parseInt(commands[0][1]));
                    break;
                case "draw":
                    CisternDraw cd = new CisternDraw(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    ViewGame.setDrawsNames(cd, tmp);
                    ViewGame.setDrawsReverseNames(tmp, cd);
                    break;
                default:
                    break;
            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        waterCounter.addCistern(tmp);
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
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
        for (String[] command : commands) {
            if (command[0].equals("draw")) {
                SpringDraw sd = new SpringDraw(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                ViewGame.setDrawsNames(sd, tmp);
                ViewGame.setDrawsReverseNames(tmp, sd);
            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
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
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
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
        for (String[] command : commands) {
            switch (command[0]) {
                case "pump":
                    tmp.setHoldingPump((Pump) objectNames.get(command[1]));
                    break;
                case "pipe":
                    tmp.setHoldingPipe((Pipe) objectNames.get(command[1]));
                    break;
                default:
                    break;

            }
        }
        objectNames.put(cmd[1], tmp);
        objectReverseNames.put(tmp, cmd[1]);
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
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
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
    }
    /**
     * Function for switching random off and on.
     * */
    public static void random(String[] cmd){
        if (test) {
            if(cmd.length == 2){
                switch (cmd[1]){
                    case StringResourceController.FALSE: random = false;
                        outResults.add("A véletlen események ki lettek kapcsolva."); break;
                    case "true": random = true;
                        outResults.add(StringResourceController.RANDOM_ON); break;
                    default: break;
                }
            }else {
                random=true;
                outResults.add(StringResourceController.RANDOM_ON);
            }
        }
        else {
            if(cmd.length == 2){
                switch (cmd[1]){
                    case StringResourceController.FALSE: random = false;
                        logger.log(Level.INFO, "A véletlen események ki lettek kapcsolva."); break;
                    case "true": random = true;
                        logger.log(Level.INFO, StringResourceController.RANDOM_ON); break;
                    default: break;

                }
            }else {
                random=true;
                logger.log(Level.INFO, StringResourceController.RANDOM_ON);
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
        else logger.log(Level.INFO, "A pálya létrehozása sikeresen lezajlott. Kezdődhet a játék!");
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
                else logger.log(Level.INFO, p::toString);
                break;
            case "field":
                if (test) outResults.add(objectReverseNames.get(p.getStandingField()));
                else logger.log(Level.INFO, objectReverseNames.get(p.getStandingField()));
                break;
            default: break;
        }
    }
    /**
     * Function for displaying important information about the object.
     * */
    public static void showobject(String[] cmd){
        Object object = objectNames.get(cmd[1]);
        if (test) outResults.add(object.toString());
        else logger.log(Level.INFO, object::toString);
    }
    /**
     * Function for moving a player to a field.
     * */
    public static void move(String[] cmd){
        Player p = (Player)objectNames.get(cmd[1]);
        Field f = (Field)objectNames.get(cmd[2]);
       List<Field> neighbors = p.getStandingField().getNeighborFields();
        if(neighbors.contains(f)) {
            neighborsContains(p,f);
        }
        else{
            if (test) {outResults.add(StringResourceController.WRONG_ACTION);lastResult = false;}
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
                lastResult = false;
                moves--;
            }
        }
    }
    private static void neighborsContains(Player p, Field f){
        if (p.move(f)) {
            if (test) {outResults.add(StringResourceController.GOOD_ACTION);lastResult = true;}
            else {logger.log(Level.INFO, StringResourceController.GOOD_ACTION);lastResult = true;}
        } else {
            if (test) {outResults.add(StringResourceController.WRONG_ACTION);lastResult = false;}
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
            if (test) {outResults.add(StringResourceController.GOOD_ACTION); lastResult = true;}
            else {logger.log(Level.INFO, StringResourceController.GOOD_ACTION);lastResult = true;}
        }else  {
            if (test) {outResults.add(StringResourceController.WRONG_ACTION); lastResult = false;}
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            lastResult = false;
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
        ActiveFields pumpA;
        ActiveFields pumpB;
        pumpA = pumpB = null;
        try {
        	pumpA = ((Pipe)p.getStandingField()).getFields().get(0);
        	pumpB = ((Pipe)p.getStandingField()).getFields().get(1);
        } catch(Exception e) {
            logger.log(Level.WARNING, StringResourceController.INVALID_ACTION);
        }

        assert p != null;
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
            
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            lastResult = false;
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
             if (test) outResults.add(StringResourceController.GOOD_ACTION);
             else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
         }else  {
             lastResult = false;
             if (test) outResults.add(StringResourceController.WRONG_ACTION);
             else {
                 logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            lastResult = false;
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
        } catch(Exception e) {
            logger.log(Level.WARNING, StringResourceController.INVALID_ACTION);
        }
        if(player.connect()){
            lastResult = true;
            
            PipeDraw pd = (PipeDraw)ViewGame.objectDrawReverseNames.get(holdedPipe);
            assert holdedPipe != null;
            Drawable toPumpD = ViewGame.objectDrawReverseNames.get(holdedPipe.getFields().get(0));
            Drawable fromPumpD = ViewGame.objectDrawReverseNames.get(standing);
            pd.setCoords(fromPumpD, toPumpD);
            
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            lastResult = false;
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
        
            
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            lastResult = false;
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
            lastResult = true;
            
            //legyen már az új csőnek drawable-je is
            Pipe newPipe = ((Mechanic)player).getHoldingPipe();
            PipeDraw newPipeD = new PipeDraw(-100,0,0,0);
            ViewGame.setDrawsNames(newPipeD, newPipe); 
            ViewGame.setDrawsReverseNames(newPipe, newPipeD);
            
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            lastResult = false;
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            lastResult = false;
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
                moves--;
            }
        }
    }
    /**
     * Function for making a pipe slippery by a player.
     * */
    public static void makeslippery(String[] cmd){
        Player player = (Player)objectNames.get(cmd[1]);
        logger.log(Level.INFO, cmd[1]);
        if(player.makeSlippery()){
            lastResult = true;
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            lastResult = false;
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else {
                logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
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
            for (String outResult : outResults) {
                out.println(outResult);
            }
        }
        catch(FileNotFoundException e) {
            logger.log(Level.WARNING, StringResourceController.FILE_NOT_FOUND);
        }
        try(Scanner scannerResult = new Scanner(new File(cmd[1]))) {
            try(Scanner scannerExpected = new Scanner((new File(cmd[1].replace(".out", ".test"))));) {
                ArrayList<String> result = new ArrayList<>();
                ArrayList<String> expected = new ArrayList<>();
                while (scannerResult.hasNextLine()) {
                    result.add(scannerResult.nextLine().strip());
                }
                while (scannerExpected.hasNextLine()) {
                    expected.add(scannerExpected.nextLine().strip());
                }
                String separator = "\\";
                String[] tmp = cmd[1].replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
                fileName = tmp[tmp.length - 1];
                logger.log(Level.INFO, () -> "Test name: " + fileName.replace(".out", ""));
                if (result.size() != expected.size()) {
                    logger.log(Level.INFO, "Test failed. The 2 files do not have the same amount of lines.");
                    return;
                }
                int errors = getErros(result, expected);
                if (errors == 0 && !result.isEmpty() && !expected.isEmpty()) {
                    logger.log(Level.INFO, "Test succeeded.\n");
                } else {
                    logger.log(Level.INFO, "Test failed.\n");
                }
                pipes = pumps = 0;
                waterCounter.reset();
                objectNames.clear();
                objectReverseNames.clear();
            }
        }
        catch(FileNotFoundException e) {
            logger.log(Level.WARNING, StringResourceController.FILE_NOT_FOUND);
        }
        outResults.clear();
    }

    private static int getErros(ArrayList<String> result, ArrayList<String> expected ){
        int errors = 0;
        if (!result.isEmpty() && !expected.isEmpty()) {
            for (int i = 0; i < expected.size(); i++) {
                if (!result.get(i).equals(expected.get(i))) {
                    int finalI = i;
                    logger.log(Level.INFO, () -> "Error in line " + (finalI +1) + ".\nExpected: " + expected.get(finalI) + ", but got: " + result.get(finalI));
                    errors++;
                }
            }
        }
        return errors;
    }
    /**
     * Function for doing all the tests.
     * */
    public static void testAll(String[] cmd) {
        try(Scanner scanner = new Scanner(new File(cmd[1] + "\\Alltests.txt"))) {

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                load(cmd[1] + "\\" + line);
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, StringResourceController.FILE_NOT_FOUND);
        }
    }
    /**
     * Function for listing all objects in the game.
     * */
    public static void list(){
        for (Object obj : objectNames.values()) {
            logger.log(Level.INFO, () -> objectReverseNames.get(obj) + " ");
        }        logger.log(Level.INFO, "");
    }
    /**
     * Function for putting a player on a field.
     * */
    public static void addplayer(String[] cmd) {
        Field f = (Field) objectNames.get(cmd[1]);
        Player p = (Player) objectNames.get(cmd[2]);
        if(f.accept(p) != null) {
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
        }
    }

    public static void step(String[] cmd){
        Steppable s = (Steppable)objectNames.get(cmd[1]);
        s.step();
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
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
                Steppable value = (Steppable) obj;
                value.step();
            }
        }
        logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
    }
    /**
     * Function for countig the points for the two sides.
     * */
    public static void count(String[] cmd){
        waterCounter.count();
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
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
        logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        if(!test) gameMode = false;
    }
    /**
     * Function for putting the game into test mode.
     * */
    public static void test(String[] cmd){
        if(cmd[1].equals("true")) test=true;
        else if(cmd[1].equals(StringResourceController.FALSE)) test=false;
        logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
    }
    /**
     * Function for signaling to the watercounter that the game ended.
     * */
    public static void setend(String[] cmd){
        waterCounter.setEnd();
        if (test) outResults.add(StringResourceController.GOOD_ACTION);
        else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
    }
    /**
     * Function for setting the in/out pipes of a pump.
     * */
    public static void setpump(String[] cmd){
        Pump pump = (Pump)objectNames.get(cmd[1]);
        if(pump.set((Pipe)objectNames.get(cmd[2]), (Pipe)objectNames.get(cmd[3]))){
            if (test) outResults.add(StringResourceController.GOOD_ACTION);
            else logger.log(Level.INFO, StringResourceController.GOOD_ACTION);
        }else  {
            if (test) outResults.add(StringResourceController.WRONG_ACTION);
            else logger.log(Level.INFO, StringResourceController.WRONG_ACTION);
        }
    }
}
