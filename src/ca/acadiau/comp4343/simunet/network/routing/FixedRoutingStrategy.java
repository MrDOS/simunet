package ca.acadiau.comp4343.simunet.network.routing;

import java.util.List;

import org.apache.commons.collections15.BidiMap;

import ca.acadiau.comp4343.simunet.network.Connection;
import ca.acadiau.comp4343.simunet.network.Node;
import ca.acadiau.comp4343.simunet.network.Packet;

/**
 * Route packets according to a fixed routing table.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class FixedRoutingStrategy implements RoutingStrategy
{
    public List<Node> nextNode(Packet packet, BidiMap<Node, Connection> adjacentNodes) throws RoutingException
    {
        return null;
    }
}