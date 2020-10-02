package bounce;
/**
 * Class to represent an oval.
 * 
 * @author Charmaine Wu
 */

public class OvalShape extends Shape{
    /**
	 * Creates an OvalShape object with default values for instance variables.
	 */
	public OvalShape() {
		super();
	}
	/**
	 * Creates an OvalShape object with a specified x and y position.
	 */
	public OvalShape(int x, int y) {
		super(x,y);
	}
	/**
	 * Creates an OvalShape instance with specified x, y, deltaX and deltaY values.
	 * The Shape object is created with a default width and height.
	 */
	public OvalShape(int x, int y, int deltaX, int deltaY) {
		super(x, y, deltaX, deltaY);
	}
	/**
	 * Creates an OvalShape instance with specified x, y, deltaX, deltaY, width and
	 * height values.
	 */
	public OvalShape(int x, int y, int deltaX, int deltaY, int width, int height) {
		super(x, y, deltaX, deltaY, width, height);
    }
    /**
	 * Paints the oval using the supplied Painter.
	 */
	@Override
	public void paint(Painter painter) {
		painter.drawOval(_x,_y,_width,_height);
	}
}