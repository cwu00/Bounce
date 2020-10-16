package bounce;

import java.util.ArrayList;
import java.util.List;

public class NestingShape extends Shape {
    // list containing all children of the nested shape
    private List<Shape> _nestedChildren = new ArrayList<Shape>();

    public NestingShape(){ 
        super(DEFAULT_X_POS, DEFAULT_Y_POS, DEFAULT_DELTA_X, DEFAULT_DELTA_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    public NestingShape(int x, int y){ 
        super(x, y, DEFAULT_DELTA_X, DEFAULT_DELTA_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    public NestingShape(int x, int y, int deltaX, int deltaY){ 
        super(x,y,deltaX,deltaY, DEFAULT_WIDTH, DEFAULT_HEIGHT); 
    }
    public NestingShape(int x, int y, int deltaX, int deltaY, int width, int height){
        super(x,y,deltaX,deltaY,width,height); 
    }
    public NestingShape(int x, int y, int deltaX, int deltaY, int width, int height, String text){
        super(x,y,deltaX,deltaY,width,height, text); 
    }

    /**
     * Paints a NestingShape object by drawing a rectangle around the edge of its bounding box. 
     * Once the NestingShape’s border has been painted, a NestingShape paints its children.
     */
    @Override
    protected void doPaint(Painter paint){
        paint.drawRect(_x, _y, _width, _height);
        paint.translate(_x, _y);
        for (Shape s : _nestedChildren){
            s.paint(paint);
        }
        paint.translate(-_x, -_y);
    }

    /**
    * Moves a NestingShape object (including its children) within the bounds specified by arguments 
    * width and height. A NestingShape first moves itself, and then moves its children.
    */
    @Override
    public void move(int width, int height){
        super.move(width, height);
        for (Shape s : _nestedChildren){
            s.move(_width,_height);
        }
    }

    /**
    * Attempts to add a Shape to a NestingShape object. If successful, a two−way link is established 
    * between the NestingShape and the newly added Shape.
    ∗ @param shape the shape to be added.
    ∗ @throws IllegalArgumentException if an attempt is made to add a Shape to a NestingShape
    * instance where the Shape argument is already a child within a NestingShape instance, 
    * or an attempt is made to add a Shape that will not fit 
    * within the bounds of the proposed NestingShape object.
    */
    void add (Shape shape) throws IllegalArgumentException{
        if ((shape._x + shape._width > this._x + this._width) || (shape._y + shape._height > this._y + this._height)){
            throw new IllegalArgumentException("This shape does not fit");
        } else if (shape.parent() != null){
            throw new IllegalArgumentException("Shape already exists in a nesting shape");
        }
        // establishing a two-way link 
        shape.setParent(this);
        _nestedChildren.add(shape);
    }

    void remove (Shape shape){
        if (! _nestedChildren.contains(shape)){ return; }
        shape.setParent(null);
        _nestedChildren.remove(shape);
    }

    public Shape shapeAt (int index) throws IndexOutOfBoundsException {
        if (index > _nestedChildren.size()-1){
            throw new IndexOutOfBoundsException("this number exceeds the number of children currently nested");
        } else if (index < 0){
            throw new IndexOutOfBoundsException("this index is not valid");
        }
        return _nestedChildren.get(index);
    }

    public int shapeCount(){ return _nestedChildren.size(); }

    public int indexOf (Shape shape) { return _nestedChildren.indexOf(shape); }

    public boolean contains (Shape shape) { return _nestedChildren.contains(shape); }
}