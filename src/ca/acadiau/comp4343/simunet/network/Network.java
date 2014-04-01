package ca.acadiau.comp4343.simunet.network;

import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import ca.acadiau.comp4343.simunet.network.routing.RoutingStrategy;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * A network of {@link Node} objects connected via {@link Connection}s.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class Network extends SparseGraph<Node, Connection>
{
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private RoutingStrategy strategy;

    private boolean tickConnections = false;

    private final HashSet<NodeListener> listeners = new HashSet<NodeListener>();

    public void addNodeListener(NodeListener listener)
    {
        this.listeners.add(listener);
    }

    public void removeNodeListener(NodeListener listener)
    {
        this.listeners.remove(listener);
    }

    @Override
    public boolean addEdge(Connection connection, Node leftNode, Node rightNode, EdgeType edgeType)
    {
        if (!super.addEdge(connection, leftNode, rightNode, edgeType))
            return false;

        connection.setLeftNode(leftNode);
        connection.setRightNode(rightNode);

        leftNode.onConnectionTo(rightNode, connection);
        rightNode.onConnectionTo(leftNode, connection);
        return true;
    }

    @Override
    public boolean addVertex(Node node)
    {
        if (!super.addVertex(node))
            return false;

        for (NodeListener listener : this.listeners)
            listener.onNodeAdded(node);

        return true;
    }

    @Override
    public boolean removeEdge(Connection connection)
    {
        if (!super.removeEdge(connection))
            return false;

        connection.getLeftNode().onDisconnectionFrom(connection.getRightNode(), connection);
        connection.getRightNode().onDisconnectionFrom(connection.getLeftNode(), connection);

        return true;
    }

    @Override
    public boolean removeVertex(Node node)
    {
        if (!super.removeVertex(node))
            return false;

        for (NodeListener listener : this.listeners)
            listener.onNodeRemoved(node);

        return true;
    }

    public void tick()
    {
        if (this.strategy == null)
            throw new IllegalArgumentException("A routing strategy must be provided before a tick can occur");

        if (this.tickConnections)
            for (Connection connection : this.getEdges())
                connection.tick();
        else
            for (Node node : this.getVertices())
                node.tick(this.strategy);

        this.tickConnections = !this.tickConnections;
    }
}