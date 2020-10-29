package bounce.views;

import java.util.ArrayList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import bounce.NestingShape;
import bounce.Shape;
import bounce.ShapeModel;

public class TreeModelAdapter implements TreeModel {

    // reference to the shape object that is to be represented by this TreeModel
    private ShapeModel _adaptee;

    // list of TreeModelListners
    protected ArrayList<TreeModelListener> _treeModelListeners;

    // creates a TreeModelAdapter and set the shape to be represented
    public TreeModelAdapter(ShapeModel shape) {
        _adaptee = shape;
        _treeModelListeners = new ArrayList<TreeModelListener>();
    }

    /**
     * Returns the root of the shape model
     */
    public Shape getRoot() {
        return _adaptee.root();
    }

    /**
     * Returns the number of children of given shape
     * @param parent
     */
    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof NestingShape) {
            NestingShape nesting = (NestingShape) parent;
            return nesting.shapeCount();
        }
        return 0;
    }

    /**
     * Returns true if the shape has no children
     * 
     * @param node
     */
    @Override
    public boolean isLeaf(Object node) {
        if (node instanceof NestingShape) {
            return false;
        }
        return true;
    }

    /**
     * Returns the index of child in parent
     * 
     * @param parent
     * @param child
     */
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof NestingShape && child instanceof Shape){
            NestingShape nesting = (NestingShape) parent;
            Shape shape = (Shape)child;
            if (nesting.contains(shape)) {
                return nesting.indexOf(shape);
            }
        }
        return -1;
    }

    /**
     * Notifies the user when the path has been modified
     * 
     * @param path
     * @param newShape
     */
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {}

    /**
     * Returns the children of given parent shape at given index
     * @param parent
     * @param index
     */
    @Override
    public Object getChild(Object parent, int index) {
        if (index >= 0 && index < getChildCount(parent)) {
            if (parent instanceof NestingShape) {
                NestingShape nesting = (NestingShape) parent;
                return nesting.shapeAt(index);
            }
        }
        return null;
    }

    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     * @param l
     */
    @Override
    public void addTreeModelListener(TreeModelListener l) {
        _treeModelListeners.add(l);
    }

    /** 
     * Removes a listener previously added with addTreeModelListener.
     * @param l
     */
    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        if (_treeModelListeners.contains(l)){
            _treeModelListeners.remove(l);
        }
    }
}
