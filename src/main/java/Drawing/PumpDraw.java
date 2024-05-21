package Drawing;

import Controll.ViewGame;
import Fields.ActiveFields.Pump;

import javax.swing.*;
import java.awt.*;

/**
 * Class for drawing the mechanic.
 * */
public class PumpDraw extends Drawable {
    JButton pump = new JButton();
    JButton pumpB = new JButton();
    /**
     * Constructor for the mechanic.
     * @param tmpX X coordinate of the mechanic.
     * @param tmpY Y coordinate of the mechanic.
     * */
    public PumpDraw(int tmpX, int tmpY) {
        x = tmpX;
        y = tmpY;
        pump.setVisible(true);
        pump.setBackground(new Color(150, 75, 0));
        
        ViewGame.buttonToElement.put(pumpB, this);
        pumpB.addActionListener(ViewGame.selectListener);
    }
    /**
     * Draws the mechanic.
     * @param panel The panel to draw on.
     * @param g The graphics to draw with.
     * */
    public void Draw(JPanel panel, Graphics2D g) {
        pump.setBounds(x, y, 50, 50);
        Pump p = (Pump)ViewGame.objectDrawNames.get(this);

        if (p.isBroken()) pump.setBorder(BorderFactory.createDashedBorder(Color.black, 5, 2, 2, false));
        else pump.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        if(ViewGame.getChosen()) {
        	pumpB.setVisible(true);
        	pumpB.setBackground(new Color(255, 0, 0));
        	pumpB.setBounds(x, y, 50, 50);
        	if (p.isBroken()) pumpB.setBorder(BorderFactory.createDashedBorder(Color.black, 5, 2, 2, false));
            else pumpB.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        	panel.add(pumpB);
        } else {
        	pumpB.setVisible(false);
        	panel.remove(pumpB);
        }
        
        panel.add(pump);
        panel.repaint();
    }
}
