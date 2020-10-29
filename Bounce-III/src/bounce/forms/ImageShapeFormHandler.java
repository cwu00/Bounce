package bounce.forms;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingWorker;

import bounce.ImageRectangleShape;
import bounce.NestingShape;
import bounce.ShapeModel;
import bounce.forms.util.Form;
import bounce.forms.util.FormHandler;

public class ImageShapeFormHandler implements FormHandler{

    private ShapeModel _model;
    private NestingShape _parentOfNewShape;
	private SwingWorker<BufferedImage, Void> _worker;
	
	/**
	 * Creates a ImageShapeFormHandler.
	 * 
	 * @param model the ShapeModel to which the handler should add a newly 
	 *        constructed ImageRectangleShape object. 
	 * @param parent the NestingShape object that will serve as the parent for
	 *        a new ImageRectangleShape instance.
	 */
	public ImageShapeFormHandler(ShapeModel model, NestingShape parent) {
		_model = model;
		_parentOfNewShape = parent;
	}

    /**
	 * Calls the background worker to rescale the large image
	 */
    @Override
    public void processForm(Form form) {
		_worker = new ImageShapeFormHandlerWorker(form);
		_worker.execute();
	}
	
	/**
	 * Worker thread that scales the large image in background
	 */
	private class ImageShapeFormHandlerWorker extends SwingWorker<BufferedImage, Void>{
		private Form _form;

		public ImageShapeFormHandlerWorker(Form form){
			_form = form;
		}

		/**
		 * Uses a backgound thread.
		 * Reads form data that describes an ImageRectangleShape.
		 * creates a new ImageRectangleShape object, 
		 * adds it to a ShapeModel and to a NestingShape within the model.
		 */
		@Override
		protected BufferedImage doInBackground() throws Exception {
			Form form = _form;
		
			// Read field values from the form.
			File imageFile = (File)form.getFieldValue(File.class, ImageFormElement.IMAGE);
			int width = form.getFieldValue(Integer.class, ShapeFormElement.WIDTH);
			int deltaX = form.getFieldValue(Integer.class, ShapeFormElement.DELTA_X);
			int deltaY = form.getFieldValue(Integer.class, ShapeFormElement.DELTA_Y);
			
	
			// Load the original image (ImageIO.read() is a blocking call).
			BufferedImage fullImage = null;
			try {
				fullImage = ImageIO.read(imageFile);
			} catch(IOException e) {
				System.out.println(e);
				System.out.println("Error loading image.");
			}
			
			int fullImageWidth = fullImage.getWidth();
			int fullImageHeight = fullImage.getHeight();
					
			BufferedImage scaledImage = fullImage;
					
			// Scale the image if necessary.
			if(fullImageWidth > width) {
				double scaleFactor = (double)width / (double)fullImageWidth;
				int height = (int)((double)fullImageHeight * scaleFactor);
						
				scaledImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
				Graphics2D g = scaledImage.createGraphics();
						
				// Method drawImage() scales an already loaded image. The 
				// ImageObserver argument is null because we don't need to monitor 
				// the scaling operation.
				g.drawImage(fullImage, 0, 0, width, height, null);
			}
			
			// Create the new Shape and add it to the model.
			ImageRectangleShape imageShape = new ImageRectangleShape(deltaX, deltaY, scaledImage);
			_model.add(imageShape, _parentOfNewShape);
			
			return null;
		}	
	}    
}
