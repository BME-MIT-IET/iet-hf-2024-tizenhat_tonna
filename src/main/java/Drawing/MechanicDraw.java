package main.java.Drawing;

import main.java.Controll.Controller;
import main.java.Controll.ViewGame;
import main.java.Fields.ActiveFields.Cistern;
import main.java.Fields.ActiveFields.Pump;
import main.java.Fields.ActiveFields.Spring;
import main.java.Fields.Field;
import main.java.Fields.Pipe;
import main.java.Players.Mechanic;
import main.java.Players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class for drawing the mechanic.
 * */
public class MechanicDraw extends Drawable {
    String mecName = "";
    JButton mec = new JButton();

    /**
     * Constructor for the mechanic.
     * @param tmpX X coordinate of the mechanic.
     * @param tmpY Y coordinate of the mechanic.
     * */
    public MechanicDraw(int tmpX, int tmpY) {
        x = tmpX;
        y = tmpY;
        mec.setVisible(true);
    }

    /**
     * Draw method for the mechanic.
     * @param panel
     * @param g
     */
    @Override
    public void Draw(JPanel panel, Graphics2D g) {
        Mechanic m = (Mechanic)ViewGame.objectDrawNames.get(this);
        Player current = Controller.GetActivePlayer();
        mecName = Controller.objectReverseNames.get(m);
        mec.setText(mecName);
        Field f = m.getStandingField();
        if (f instanceof Pipe) {
            PipeDraw pd = (PipeDraw)ViewGame.objectDrawReverseNames.get(f);
            ArrayList<Player> players = f.getPlayers();
            int i = players.indexOf(m);
            x = (pd.getxFrom()+pd.getxTo())/2-25;
            y = (pd.getYFrom()+pd.getYTo())/2 - (i+1)*25;
        }
        else if (f instanceof Pump) {
            PumpDraw pd = (PumpDraw)ViewGame.objectDrawReverseNames.get(f);
            ArrayList<Player> players = f.getPlayers();
            int i = players.indexOf(m);
            x = pd.getX();
            y = pd.getY() - (i+1)*25;
        }
        else if (f instanceof Cistern) {
            CisternDraw pd = (CisternDraw)ViewGame.objectDrawReverseNames.get(f);
            ArrayList<Player> players = f.getPlayers();
            int i = players.indexOf(m);
            x = pd.getX();
            y = pd.getY() - (i+1)*25;
        }
        else if (f instanceof Spring) {
            SpringDraw pd = (SpringDraw)ViewGame.objectDrawReverseNames.get(f);
            ArrayList<Player> players = f.getPlayers();
            int i = players.indexOf(m);
            x = pd.getX();
            y = pd.getY() - (i+1)*25;
        }
        mec.setBounds(x, y, 50, 20);
        if (m.equals(current)) mec.setBorder(BorderFactory.createLineBorder(Color.green, 5));
        else mec.setBorder(BorderFactory.createLineBorder(Color.red, 5));
        panel.add(mec);
        panel.repaint();
    }
}
