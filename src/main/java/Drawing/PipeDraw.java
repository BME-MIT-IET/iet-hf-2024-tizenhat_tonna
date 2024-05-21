package Drawing;

import Controll.ViewGame;

import javax.swing.*;
import java.awt.*;

/**
 * PipeDraw class
 */
public class PipeDraw extends Drawable {
    private int xFrom;
    private int xTo;
    private int yFrom;
    private int yTo;
    JButton pipeB = new JButton();

    /**
     * Getters
     * @return xFrom, xTo, yFrom, yTo
     */
    public int getxFrom() { return xFrom; }
    public int getxTo() { return xTo; }
    public int getYFrom() { return yFrom; }
    public int getYTo() { return yTo; }

    /**
     * Constructor for PipeDraw
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public PipeDraw(int x1, int y1, int x2, int y2) {
        xFrom = x1;
        yFrom = y1;
        xTo = x2;
        yTo = y2;

        ViewGame.buttonToElement.put(pipeB, this);
        pipeB.addActionListener(ViewGame.selectListener);
    }
    public void setCoords(Drawable pumpFrom, Drawable pumpTo) {
    	int fromX, toX, fromY, toY;
    	
    	fromX = pumpFrom.getX();
        fromY = pumpFrom.getY();
        toX = pumpTo.getX();
        toY = pumpTo.getY();
    	
    	//A cél pumpa
        if(toY < fromY) {
        	//felettünk van
        	toY += 50;
        } else if(toY > fromY) {
        	//alattunk
        	fromY += 50;
        } else {
        	//velünk egy magasságban
        	toY += 25;
        	fromY += 25;
        	
        }
        if(toX < fromX) {
        	//balra
        	toX += 50;
        } else if(fromX > toX) {
        	//jobbra
        	fromX += 50;
        } else {
        	//egy oszlopban
        	toX += 25;
        	fromX += 25;
        }
        setCoords(fromX, fromY, toX, toY);
    	
    	
    }
    public void setCoords(int x1, int y1, int x2, int y2) {
    	xFrom = x1;
        yFrom = y1;
        xTo = x2;
        yTo = y2;
    }

    /**
     * Draw method
     * @param panel
     * @param g2d
     */
    @Override
    public void Draw(JPanel panel, Graphics2D g2d) {

    	if(ViewGame.getChosen()) {
    		pipeB.setVisible(true);
    		pipeB.setBackground(new Color(255, 0, 0));
    		pipeB.setBounds(xFrom + (xTo - xFrom) / 2 - 25, yFrom + (yTo - yFrom) / 2 - 12, 50, 25);
    		pipeB.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        	panel.add(pipeB);
        } else {
        	pipeB.setVisible(false);
        	panel.remove(pipeB);
        }
    	
    	panel.repaint();
    }
}
