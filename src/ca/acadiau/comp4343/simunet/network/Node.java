package ca.acadiau.comp4343.simunet.network;

import java.util.LinkedList;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.commons.collections15.bidimap.DualHashBidiMap;

import ca.acadiau.comp4343.simunet.network.routing.RoutingException;
import ca.acadiau.comp4343.simunet.network.routing.RoutingStrategy;

/**
 * A node on the network.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
@RequiredArgsConstructor
public class Node implements ConnectionListener<Node, Connection>
{
    private static final Logger LOGGER = Logger.getLogger(Node.class.getSimpleName());

    @Getter
    private final int id;
    @Getter
    private final DualHashBidiMap<Node, Connection> adjacentNodes = new DualHashBidiMap<Node, Connection>();

    private final LinkedList<Packet> packets = new LinkedList<Packet>();
    @Getter
    private int load = 0;

    @Override
    public String toString()
    {
        return String.valueOf(this.id);
    }

    @Override
    public void onConnectionTo(Node node, Connection connection)
    {
        this.adjacentNodes.put(node, connection);
    }

    @Override
    public void onDisconnectionFrom(Node node, Connection connection)
    {
        this.adjacentNodes.remove(node);
    }

    public void receive(Packet packet)
    {
        /* If the packet is destined for this node, we can remove it from the
         * flow of traffic. */
        if (packet.getDestination().equals(this))
        {
            LOGGER.info(this + " received a packet");
            return;
        }

        packet.getPath().add(this);

        /* Otherwise, we need to forward it. */
        this.packets.add(packet);
        this.load++;
    }

    public void tick(RoutingStrategy strategy)
    {
        /* Send traffic scheduled as outgoing. */
        while (this.packets.size() > 0)
        {
            Packet packet = this.packets.pop();
            this.load--;

            try
            {
                for (Node nextNode : strategy.nextNode(packet, this.adjacentNodes))
                    this.adjacentNodes.get(nextNode).enqueue(packet, nextNode);
            }
            catch (RoutingException e)
            {
                LOGGER.info("Unroutable packet");
            }
        }
    }
}