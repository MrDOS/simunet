package ca.acadiau.comp4343.simunet.network;

import org.apache.commons.collections15.Factory;

/**
 * Produces instances of {@link Node}s.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class NodeFactory implements Factory<Node>
{
    private static NodeFactory instance;

    private int counter = 0;

    private NodeFactory()
    {
    }

    public static NodeFactory getInstance()
    {
        if (NodeFactory.instance == null)
            NodeFactory.instance = new NodeFactory();
        return NodeFactory.instance;
    }

    @Override
    public Node create()
    {
        return new Node(++this.counter);
    }
}