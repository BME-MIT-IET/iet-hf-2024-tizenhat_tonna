package Drawing;

import Controll.ViewGame;

import javax.swing.*;
import java.awt.*;

public class SpringDraw extends Drawable {
    JButton spring = new JButton();
    JButton springB = new JButton();

    public SpringDraw(int tmpX, int tmpY) {
        x = tmpX;
        y = tmpY;
        spring.setVisible(true);
        spring.setBackground(new Color(150, 75, 0));
        
        ViewGame.buttonToElement.put(springB, this);
        springB.addActionListener(ViewGame.selectListener);
        
    }

    @Override
    public void Draw(JPanel panel, Graphics2D g) {
        spring.setBounds(x, y, 50, 50);
        spring.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        
        if(ViewGame.getChosen()) {
        	springB.setVisible(true);
        	springB.setBackground(new Color(255, 0, 0));
        	springB.setBounds(x, y, 50, 50);
        	springB.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        	panel.add(springB);
        } else {
        	springB.setVisible(false);
        	panel.remove(springB);
        }
        
        panel.add(spring);
        panel.repaint();
    }
}
