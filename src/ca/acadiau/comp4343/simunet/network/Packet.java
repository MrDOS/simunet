package ca.acadiau.comp4343.simunet.network;

import java.util.LinkedList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A packet traversing the network.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
@RequiredArgsConstructor
public class Packet
{
    /**
     * @var the path the packet has followed
     */
    @Getter
    private final LinkedList<Node> path = new LinkedList<Node>();
    /**
     * @var the node for which the packet is destined
     */
    @Getter
    private final Node destination;

    /**
     * Indicate to the packet that it has moved to a new node.
     * 
     * @param node the node to which the packet has moved
     */
    public void moveTo(Node node)
    {
        this.path.add(node);
    }
}