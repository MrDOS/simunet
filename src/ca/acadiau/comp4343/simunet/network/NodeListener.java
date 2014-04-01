package ca.acadiau.comp4343.simunet.network;

/**
 * Something that listens for the addition to or removal of {@link Node}s from a
 * {@link Network}.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public interface NodeListener
{
    /**
     * Called when a node is added to the {@link Network}.
     * 
     * @param node the node that has been added
     */
    public void onNodeAdded(Node node);

    /**
     * Called when a node is removed from the {@link Network}.
     * 
     * @param node the node that has been removed
     */
    public void onNodeRemoved(Node node);
}