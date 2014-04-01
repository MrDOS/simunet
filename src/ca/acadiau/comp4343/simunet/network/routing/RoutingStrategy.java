package ca.acadiau.comp4343.simunet.network.routing;

import java.util.Collection;

import org.apache.commons.collections15.BidiMap;

import ca.acadiau.comp4343.simunet.network.Connection;
import ca.acadiau.comp4343.simunet.network.Node;
import ca.acadiau.comp4343.simunet.network.Packet;

/**
 * An interface describing how routing strategies direct the flow of traffic
 * through nodes.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public interface RoutingStrategy
{
    /**
     * Pick the next node to which a packet should be sent. It must be possible
     * to determine the current location of the packet via
     * <code>packet.getPath().peek()</code>; that is, the packet's path must be
     * updated before calling this method.
     * 
     * @param packet the packet
     * @param adjacentNodes nodes immediately adjacent to the packet
     * @return the set of nodes to which the packet should be sent
     * @throws RoutingException if no path can be found
     */
    public Collection<Node> nextNode(Packet packet, BidiMap<Node, Connection> adjacentNodes) throws RoutingException;
}