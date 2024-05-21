package Drawing;

import javax.swing.*;
import java.awt.*;

/**
 * Drawable class
 */
public abstract class Drawable {
    protected int x;
    protected int y;

    /**
     * Get X method
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Get Y method
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Draw method
     * @param panel
     * @param g
     */
    public void Draw(JPanel panel, Graphics2D g) {
    }
}
