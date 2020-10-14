package bounce;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class to represent a rectangle. Fill colour of rectangle changes as shape
 * moves
 * 
 * @author Charmaine Wu
 */

public class DynamicRectangleShape extends RectangleShape {
    private Color colour = Color.red;
    private boolean fill = true;

    public DynamicRectangleShape(){
        super(DEFAULT_X_POS, DEFAULT_Y_POS, DEFAULT_DELTA_X, DEFAULT_DELTA_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height){
        super(x, y, deltaX, deltaY, width, height);
    }

    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height, Color colour){
        super(x, y, deltaX, deltaY, width, height);
        this.colour = colour;
    }

    /**
     * Moves the shape within the specified boundaries.
     * Checks if shape has hit any boundary and decide 
     * if the shape will be filled in with specified colour
     * or have only a black outline.
     */
    @Override
    public void move(int width, int height) {
        super.move(width, height);
        
        int nextY = _y + _deltaY;
        int nextX = _x + _deltaX;
        
        // hitting horizontal wall
        if ((nextY <= 0) || (nextY + _height >= height)){
            fill = false;
        }
        // checks if at top or bottom boundary
        if (_y ==0 || _y + _height == height){
            fill = false;
        }
        // hitting a vertical wall
        if (nextX <= 0 || nextX + _width >= width){
            fill = true;
        }
        // checks if at vertical boundary
        if (_x == 0 || _x + _width == width){
            fill = true;
        }
    }

    /**
	 * Paints the rectangle based on whether it has
     * hit a boundary.
	 */
    @Override
    public void paint(Painter paint){
        Color currentColor = paint.getColor();
        if (fill){
            paint.setColor(colour);
            paint.fillRect(_x, _y, _width, _height);
            paint.setColor(currentColor);
        } else {
            super.paint(paint);
        }
    }
}