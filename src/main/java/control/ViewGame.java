package main.java.control;

import main.java.drawing.Drawable;
import main.java.drawing.PipeDraw;
import main.java.enums.Fluid;
import main.java.fields.Pipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewGame extends JFrame implements ActionListener {

    private static boolean isChosen = false;
    private JPanel gameBackground;

    /**
     * HashMap for the Drawables
     */
    public static final Map<Drawable, Object> objectDrawNames = new HashMap<>();
    /**
     * HashMap for the Drawables
     */
    public static final Map<Object, Drawable> objectDrawReverseNames = new HashMap<>();


    public static ViewGame vg;

    /**
     * Infos
     */
    static JLabel activePlayer;
    JLabel labelPoints;
    static JLabel mecPoints;
    static JLabel sabPoints;
    static JLabel successLabel;
    /**
     * Controll buttons
     */
    static JButton moveButton;
    static JButton repairButton;
    static JButton breakButton;
    static JButton makeSlipperyButton;
    static JButton makeStickyButton;
    static JButton pickUpButton;
    static JButton putDownButton;
    static JButton setPumpButton;

    /**
     * Last action
     */
    static JButton lastAction; //utoljára megnyomott gomb
    /**
     * ArrayList for the selected elements
     */
    static final List<Object> selectSequence = new ArrayList<>(); //kiválasztott elemek, kiválasztásuk sorrendjében
    /**
     * HashMap for the buttons
     */
    public static final Map<JButton, Drawable> buttonToElement = new HashMap<>(); //kiválasztó gomb -> kiválasztott rajz

    /**
     * Max number of rounds
     */
    private static int maxRounds = 20;
    /**
     * Number of rounds
     */
    private static int round = 1;
    
    
    // Ez hívodik, amikor kiválasztunk egy elemet
    /**
     * Action listener for the buttons
     */
    public static ActionListener selectListener = new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
			Drawable selectedDrawing = buttonToElement.get(e.getSource());
			Object selected = objectDrawNames.get(selectedDrawing); //a kiválasztott elem
			selectSequence.add(selected);
			String[] cmd = new String[10];
			
			
			//Ez egy nagyon szar módja a feltételnek
			//De így megússzuk, hogy az összes gombot static-á tegyük
			if(lastAction.getText().equals("Pick up")) {
				cmd[1] = Controller.getActivePlayerName();
				//ezzel még lesz munka
				
				//Ha elvégeztük a teendőt, ezzel kell befejezni
				isChosen = false;
				selectSequence.clear();
                changeText(cmd);
			}
			if(lastAction.getText().equals("Set pump") && selectSequence.size() == 2) {
				cmd[1] = Controller.getActivePlayerName();
				cmd[2] = Controller.objectReverseNames.get(selectSequence.get(0));
				cmd[3] = Controller.objectReverseNames.get(selectSequence.get(1));
				Controller.set(cmd);
				isChosen = false;
				selectSequence.clear();
                changeText(cmd);
			}
			if(lastAction.getText().equals("Move")){
                cmd[1] = Controller.getActivePlayerName();
                cmd[2] = Controller.objectReverseNames.get(selectSequence.get(0));
                Controller.move(cmd);
                isChosen = false;
                selectSequence.clear();
                changeText(cmd);
            }

			vg.repaint();
		}
    };

    public static Menu menu;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args){
        // measure startup time
        long startTime = System.currentTimeMillis();

        menu = new Menu("Menu", "White");
        menu.showMenu();

        // measure startup time
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Startup time: " + duration + "ms");
    }

    public static Menu getMenuInstance() {
        return menu;
    }

    /**
     * Setter for the objectDrawNames
     */
    public static void setDrawsNames(Drawable d, Object o) { objectDrawNames.put(d, o); }

    /**
     * Setter for the reverse of the objectDrawNames
     * @param o
     * @param d
     */
    public static void setDrawsReverseNames(Object o, Drawable d) { objectDrawReverseNames.put(o, d); }

    /**
     * Get chosen method
     */
    public static boolean getChosen(){
        return isChosen;
    }


    /**
     * Constructor responsible for the game window initialization
     */
    public ViewGame() {
        setTitle("Sivatagi vízhálózatok üzemeltetése a gyakorlatban 2");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setBounds(400, 150, 1000, 700);
        setLayout(new BorderLayout());
        gameBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D)g;
                g2d.setColor(Color.black);
                super.paintComponent(g2d);
                for (Drawable draw : objectDrawReverseNames.values()){
                    if (draw instanceof PipeDraw) {
                        Pipe p = (Pipe)ViewGame.objectDrawNames.get(draw);
                        float[] dash1 = { 2f, 0f, 2f };
                        if (p.isBroken()) g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.0f, dash1, 2f));
                        else g2d.setStroke(new BasicStroke(5));
                        if (p.getFluid().equals(Fluid.DRY)) g2d.setColor(Color.black);
                        else if (p.getFluid().equals(Fluid.SLIPPERY)) g2d.setColor(Color.blue);
                        else if (p.getFluid().equals(Fluid.STICKY)) g2d.setColor(Color.yellow);
                        g2d.drawLine(((PipeDraw) draw).getxFrom(), ((PipeDraw) draw).getYFrom(), ((PipeDraw) draw).getxTo(), ((PipeDraw) draw).getYTo());
                    }
                }
            }
        };
        gameBackground.setBounds(0,0, 800, 500);
        gameBackground.setLayout(null);
        add(gameBackground, BorderLayout.CENTER);


        JPanel controllButtons = new JPanel();
        controllButtons.setLayout(new GridLayout(1,7));
        moveButton = new JButton("Move");
        repairButton = new JButton("Repair");
        setPumpButton = new JButton("Set pump");
        breakButton  = new JButton("Break");
        makeSlipperyButton = new JButton("Make Slippery");
        makeStickyButton = new JButton("Make Sticky");
        pickUpButton = new JButton("Pick up");
        putDownButton = new JButton("Put down");
        moveButton.addActionListener(this);
        repairButton.addActionListener(this);
        setPumpButton.addActionListener(this);
        breakButton.addActionListener(this);
        makeSlipperyButton.addActionListener(this);
        makeStickyButton.addActionListener(this);
        pickUpButton.addActionListener(this);
        putDownButton.addActionListener(this);
        controllButtons.add(moveButton);
        controllButtons.add(repairButton);
        controllButtons.add(setPumpButton);
        controllButtons.add(breakButton);
        controllButtons.add(makeSlipperyButton);
        controllButtons.add(makeStickyButton);
        controllButtons.add(pickUpButton);
        controllButtons.add(putDownButton);
        add(controllButtons,BorderLayout.SOUTH);

        JPanel text = new JPanel();
        text.setLayout(new GridLayout(4,1));
        activePlayer = new JLabel("Active Player:   ");
        labelPoints = new JLabel("Points:     ");
        mecPoints = new JLabel("Mechanic:    ");
        sabPoints = new JLabel("Saboteur:    ");
        successLabel= new JLabel("Last Action:      ");
        text.add(activePlayer);
        JPanel points = new JPanel();
        points.setLayout(new GridLayout(3,1));
        points.add(labelPoints);
        points.add(mecPoints);
        points.add(sabPoints);
        text.add(successLabel);
        text.add(points);
        add(text,BorderLayout.EAST);
        activePlayer.setText("Active Player: "+ Controller.getActivePlayerName());
        gameBackground.setBackground(new Color(150, 75, 0));
        setVisible(true);
        repaint();
        vg = this;
    }

    /**
     * Paints the objects
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        DrawAll(g2d);
    }

    /**
     * Changes the background color
     * @param theme
     */
    public void setBackgroundColor(String theme) {
        if (theme.equals("Black")) getContentPane().setBackground(Color.darkGray);
    }

    /**
     * Draws all the objects
     * @param g
     */
    public void DrawAll(Graphics2D g) {
        for (Drawable draw : objectDrawReverseNames.values()) {
            draw.Draw(gameBackground, g);
        }
    }

    /**
     * Action handler method
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        String[] cmd = new String[10];
        lastAction = (JButton)e.getSource(); //innen tudjuk, melyik gombot lett utoljára nyomva
        boolean successful = false;
        if(e.getSource() == moveButton){
            isChosen = true;
        }
        if(e.getSource() == repairButton){
            isChosen = false;
            cmd[1] = Controller.getActivePlayerName();
            Controller.repair(cmd);
            successful = true;
        }
        if(e.getSource() == setPumpButton){
            //e gombra kattintva kéne a pumpát átállítani
        	//Úgy lenne a legkényelmesebb, ha először kiválasztjuk honnan, aztán hová
        	isChosen = true;
        }
        if(e.getSource() == breakButton){
            isChosen = false;
            cmd[1] = Controller.getActivePlayerName();
            Controller.breakfield(cmd);
            successful = true;
        }
        if(e.getSource() == makeSlipperyButton){
            isChosen = false;
            cmd[1] = Controller.getActivePlayerName();
            Controller.makeslippery(cmd);
            successful = true;
        }
        if(e.getSource() == makeStickyButton){
            isChosen = false;
            cmd[1] = Controller.getActivePlayerName();
            Controller.makesticky(cmd);
            successful = true;
        }
        if(e.getSource() == pickUpButton){
            isChosen = true;
        }
        if(e.getSource() == putDownButton){
        	cmd[1] = Controller.getActivePlayerName();
        	if(Controller.getActivePlayer().getStandingField() instanceof Pipe) Controller.placepump(cmd);
        	else Controller.connect(cmd);
        	successful = true;
        }
        if(successful) {
            changeText(cmd);
        }
        this.repaint();
    }

    /**
     * A function that changes the text on the right side of the screen.
     * @param cmd
     */
    public static void changeText(String[] cmd){
        boolean b = Controller.changeActivePlayer();
        activePlayer.setText("Active Player: " + Controller.getActivePlayerName());
        if (b) {
            Controller.endturn();
            mecPoints.setText("Mechanic: " + Controller.waterCounter.getMechanic());
            sabPoints.setText("Saboteur: " + Controller.waterCounter.getSaboteur());
            ++round;
            if(round == maxRounds){
                Controller.setend();
                Controller.endturn();
                mecPoints.setText("Mechanic: " + Controller.waterCounter.getMechanic());
                sabPoints.setText("Saboteur: " + Controller.waterCounter.getSaboteur());
                vg.THE_END();
            }

        }
        if(Controller.getLastResult()){
            successLabel.setText("Last Action: Successful");
        }else{
            successLabel.setText("Last Action: Unsuccessful");
        }
    }

    /**
     * A function called at the end of the game that disables the buttons and prints the name of the winning player.
     */
    public void THE_END(){
        moveButton.setEnabled(false);
        repairButton.setEnabled(false);
        breakButton.setEnabled(false);
        makeSlipperyButton.setEnabled(false);
        makeStickyButton.setEnabled(false);
        pickUpButton.setEnabled(false);
        putDownButton.setEnabled(false);
        setPumpButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, "The game has ended! The winner is: " + Controller.waterCounter.winner());
        this.repaint();
    }
}
