package ca.acadiau.comp4343.simunet.network;

import java.util.HashMap;
import java.util.LinkedList;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A connection between two {@link Node}s.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
@NoArgsConstructor
public class Connection
{
    @Getter
    private Node leftNode;
    @Getter
    private Node rightNode;

    private final HashMap<Node, LinkedList<Packet>> packets = new HashMap<Node, LinkedList<Packet>>();
    @Getter
    private int load = 0;
    @Getter
    private int cost = 1;

    public void setLeftNode(Node node)
    {
        if (this.leftNode != null)
            this.packets.remove(this.leftNode);
        this.leftNode = node;
        this.packets.put(this.leftNode, new LinkedList<Packet>());
    }

    public void setRightNode(Node node)
    {
        if (this.rightNode != null)
            this.packets.remove(this.rightNode);
        this.rightNode = node;
        this.packets.put(this.rightNode, new LinkedList<Packet>());
    }

    public void setCost(int cost)
    {
        if (cost < 1)
            throw new IllegalArgumentException("Cost must be >= 1");

        this.cost = cost;
    }

    /**
     * Send a packet to a node via this connection. The recipient node must be
     * either the left or right node of the connection.
     * 
     * @param packet the packet to send
     * @param to the destination node
     */
    public void enqueue(Packet packet, Node to)
    {
        if (!this.packets.containsKey(to))
            throw new IllegalArgumentException("Destination node is not a member of the connection");

        this.packets.get(to).add(packet);
        this.load++;
    }

    /**
     * Deliver all packets enqueued for a node to that node.
     * 
     * @param to the destination node
     */
    private void deliverPackets(Node to)
    {
        if (to == null)
            return;

        LinkedList<Packet> packets = this.packets.get(to);
        while (packets.size() > 0)
        {
            to.receive(packets.pop());
            this.load--;
        }
    }

    public void tick()
    {
        this.deliverPackets(this.leftNode);
        this.deliverPackets(this.rightNode);
    }
}