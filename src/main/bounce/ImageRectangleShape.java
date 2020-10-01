package bounce;

import java.awt.Image;
import java.io.File;
import java.awt.Image.*;

public class ImageRectangleShape extends RectangleShape{

    /**
     * Creates an ImageRectangleShape object with x,y coordinates and 
     * deltaX, deltaY values specified, with given image
     */
    public ImageRectangleShape(int deltaX, int deltaY, Image image){
        super(DEFAULT_X_POS, DEFAULT_Y_POS, deltaX, deltaY);
        this.image = image;
    }

    /**
     * Loads the image specified by the imageFileName and scales it
     * @param imageFileName
     * @param shapeWidth
     * @return
     */
    public static Image makeImage(String imageFileName, int shapeWidth){
        File imageFile;

        imageFile = new File(imageFileName);


    }
    
}
