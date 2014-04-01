package ca.acadiau.comp4343.simunet.network.routing;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections15.BidiMap;

import ca.acadiau.comp4343.simunet.network.Connection;
import ca.acadiau.comp4343.simunet.network.Node;
import ca.acadiau.comp4343.simunet.network.Packet;

/**
 * Send the packet in a single random direction.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class RandomRoutingStrategy implements RoutingStrategy
{
    public List<Node> nextNode(Packet packet, BidiMap<Node, Connection> adjacentNodes) throws RoutingException
    {
        int target = new Random().nextInt(adjacentNodes.size());

        Iterator<Node> nodeIterator = adjacentNodes.keySet().iterator();
        while (target-- > 0)
            nodeIterator.next();

        return Arrays.asList(nodeIterator.next());
    }
}