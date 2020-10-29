package bounce.views;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import bounce.Shape;
import bounce.ShapeModel;
import bounce.ShapeModelEvent;
import bounce.ShapeModelListener;

public class TreeModelAdapterWithShapeModelListener extends TreeModelAdapter implements ShapeModelListener {

    // resulting treeModelEvent based on shapeModelEvent
    private TreeModelEvent treeEvent;

    public TreeModelAdapterWithShapeModelListener(ShapeModel shape) {
        super(shape);
    }

	/**
	 * Called by a ShapeModel object when it has changed in some way.
     * In response, TreeModelListeners are notified if necessary,
     * TreeModelAdapter also updates itself
	 * @param event describes the way in which a particular ShapeModel object
	 * has changed.
	 */
    @Override
    public void update(ShapeModelEvent event) {
        try {
            // creator of event
            ShapeModel source = event.source();

            // path to the parent of the shape being changed
            TreePath path = new TreePath(event.parent().path().toArray());

            // position of children (length always 1 in this case)
            int[] childIndices = {event.index()};

            // identifying the shape that the change applied to (length always 1)
            Shape[] children = {event.operand()};
            
            // creating a TreeModelEvent
            treeEvent = new TreeModelEvent(source, path, childIndices, children);
        } catch (NullPointerException e){
            return;
        }

        switch(event.eventType()){
            case ShapeAdded:
                for (TreeModelListener listener : _treeModelListeners){
                    listener.treeNodesInserted(treeEvent);
                }
                break;
            case ShapeRemoved:
                for (TreeModelListener listener : _treeModelListeners){
                    listener.treeNodesRemoved(treeEvent);
                }
                break;
            default:
                break;
        }
    }
}
