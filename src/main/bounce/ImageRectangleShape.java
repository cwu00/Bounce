package bounce;

import java.awt.Image;
import java.awt.Graphics2D;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Class to represent a rectangular shaped image
 * 
 * @author Charmaine Wu
 * 
 */
public class ImageRectangleShape extends RectangleShape{
    Image image;
    static BufferedImage buffer, buffer2 = null;
    static int sw;
    static int sh;

    /**
     * Creates an ImageRectangleShape object with x,y coordinates and 
     * deltaX, deltaY values specified, with given image
     */
    public ImageRectangleShape(int deltaX, int deltaY, Image image){
        super(DEFAULT_X_POS, DEFAULT_Y_POS, deltaX, deltaY, image.getWidth(null), image.getHeight(null));
        this.image = image;
    }

    /**
     * Loads the image specified by the imageFileName and scales it
     * @param imageFileName
     * @param shapeWidth
     * @return
     */
    public static Image makeImage(String imageFileName, int shapeWidth){
        // dimensions of the new shape
        sw = shapeWidth;
        sh = DEFAULT_HEIGHT;

        int wLoaded;
        int hLoaded;
        double scaleFactor = 0;

        try {
            File imageFile = new File(System.getProperty("user.dir") + "/src/main/bounce/" + imageFileName);
            buffer = ImageIO.read(imageFile);
        } 
        catch (Exception e) { e.printStackTrace(); }

        wLoaded = buffer.getWidth();
        hLoaded = buffer.getHeight();

        buffer2 = buffer;

        // if width of image is larger than the shape width, set scale factor
        if (wLoaded > sw){
            scaleFactor = (double) sw / (double) wLoaded;
            // calculates new shape's height after scale
            sh = (int)((double) hLoaded * scaleFactor);
            buffer2 = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g = buffer2.createGraphics();
        g.drawImage(buffer, DEFAULT_X_POS, DEFAULT_Y_POS, sw, sh, null);
        return buffer2;
    }

    /**
     * Paints the image based on scaled size
     */
    protected void doPaint(Painter paint){
        _width = sw;
        _height = sh;
        paint.drawImage(this.image, _x, _y, sw, sh);
    }
}
