package ca.acadiau.comp4343.simunet.network;

/**
 * Interface for a vertex to be notified when connected to another vertex.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public interface ConnectionListener<V, E>
{
    /**
     * Called when a vertex has been connected to another vertex.
     * 
     * @param vertex the vertex to which a connection has been made
     * @param edge the edge by which the connection has been made
     */
    public void onConnectionTo(V vertex, E edge);

    /**
     * Called when a vertex has been disconnected from another vertex.
     * 
     * @param vertex the vertex which has been disconnected
     * @param edge the edge that has been removed
     */
    public void onDisconnectionFrom(V vertex, E edge);
}