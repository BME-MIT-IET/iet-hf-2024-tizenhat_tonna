package main.java.drawing;

import main.java.control.Controller;
import main.java.control.ViewGame;
import main.java.fields.Field;
import main.java.fields.Pipe;
import main.java.fields.activefields.Cistern;
import main.java.fields.activefields.Pump;
import main.java.fields.activefields.Spring;
import main.java.players.Player;
import main.java.players.Saboteur;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class for drawing the mechanic.
 * */
public class SaboteurDraw extends Drawable {
    String sabName = "";
    JButton sab = new JButton();

    /**
     * Constructor for the mechanic.
     * @param tmpX X coordinate of the mechanic.
     * @param tmpY Y coordinate of the mechanic.
     * @param tmpX
     * @param tmpY
     */
    public SaboteurDraw(int tmpX, int tmpY) {
        x = tmpX;
        y = tmpY;
        sab.setVisible(true);
    }

    /**
     * Draw method for the mechanic.
     * @param panel
     * @param g
     */
    @Override
    public void Draw(JPanel panel, Graphics2D g) {
        Saboteur s = (Saboteur)ViewGame.objectDrawNames.get(this);
        Player current = Controller.getActivePlayer();
        sabName = Controller.objectReverseNames.get(s);
        sab.setText(sabName);
        Field f = s.getStandingField();
        if (f instanceof Pipe) {
            PipeDraw pd = (PipeDraw)ViewGame.objectDrawReverseNames.get(f);
            ArrayList<Player> players = f.getPlayers();
            int i = players.indexOf(s);
            x = (pd.getxFrom()+pd.getxTo())/2-25;
            y = (pd.getYFrom()+pd.getYTo())/2 - (i+1)*25;
        }
        else if (f instanceof Pump) {
            PumpDraw pd = (PumpDraw)ViewGame.objectDrawReverseNames.get(f);
            ArrayList<Player> players = f.getPlayers();
            int i = players.indexOf(s);
            x = pd.getX();
            y = pd.getY() - (i+1)*25;
        }
        else if (f instanceof Cistern) {
            CisternDraw pd = (CisternDraw)ViewGame.objectDrawReverseNames.get(f);
            ArrayList<Player> players = f.getPlayers();
            int i = players.indexOf(s);
            x = pd.getX();
            y = pd.getY() - (i+1)*25;
        }
        else if (f instanceof Spring) {
            SpringDraw pd = (SpringDraw)ViewGame.objectDrawReverseNames.get(f);
            ArrayList<Player> players = f.getPlayers();
            int i = players.indexOf(s);
            x = pd.getX();
            y = pd.getY() - (i+1)*25;
        }
        sab.setBounds(x, y, 50, 20);
        if (s.equals(current)) sab.setBorder(BorderFactory.createLineBorder(Color.green, 5));
        else sab.setBorder(BorderFactory.createLineBorder(Color.red, 5));
        panel.add(sab);
        panel.repaint();
    }
}
