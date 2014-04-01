package ca.acadiau.comp4343.simunet.network.routing;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.collections15.BidiMap;

import ca.acadiau.comp4343.simunet.network.Connection;
import ca.acadiau.comp4343.simunet.network.Node;
import ca.acadiau.comp4343.simunet.network.Packet;

/**
 * Distributes packets to all possible nodes except for the very most-recent
 * source.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class FloodRoutingStrategy implements RoutingStrategy
{
    private static final int MAX_HOPS = 6;

    private final HashMap<Packet, Integer> packetHopCounters = new HashMap<Packet, Integer>();

    public LinkedList<Node> nextNode(Packet packet, BidiMap<Node, Connection> adjacentNodes) throws RoutingException
    {
        Integer packetHops = this.packetHopCounters.get(packet);
        if (packetHops == null)
            packetHops = 0;
        else if (packetHops < FloodRoutingStrategy.MAX_HOPS)
            packetHops++;
        else
            return new LinkedList<Node>();
        this.packetHopCounters.put(packet, packetHops);

        LinkedList<Node> nodes = new LinkedList<Node>();

        for (Node node : adjacentNodes.keySet())
            nodes.add(node);

        return nodes;
    }
}