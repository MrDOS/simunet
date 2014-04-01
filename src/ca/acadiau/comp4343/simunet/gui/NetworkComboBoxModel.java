package ca.acadiau.comp4343.simunet.gui;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import ca.acadiau.comp4343.simunet.network.Network;
import ca.acadiau.comp4343.simunet.network.Node;
import ca.acadiau.comp4343.simunet.network.NodeListener;

/**
 * Enable a {@link javax.swing.JComboBox} to observe nodes in a {@link Network}.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class NetworkComboBoxModel implements ComboBoxModel<Node>, NodeListener
{
    private final Network network;
    private Class<? extends Node> nodeType = null;

    private final ArrayList<Node> nodes = new ArrayList<Node>();
    private Object selectedItem;

    private final HashSet<ListDataListener> listeners = new HashSet<ListDataListener>();

    public NetworkComboBoxModel(Network network)
    {
        this.network = network;
        this.network.addNodeListener(this);
    }

    public NetworkComboBoxModel(Network network, Class<? extends Node> nodeType)
    {
        this(network);
        this.nodeType = nodeType;
    }

    @Override
    public void finalize() throws Throwable
    {
        this.network.removeNodeListener(this);
    }

    @Override
    public void addListDataListener(ListDataListener listener)
    {
        this.listeners.add(listener);
    }

    @Override
    public Node getElementAt(int index)
    {
        return this.nodes.get(index);
    }

    @Override
    public int getSize()
    {
        return this.nodes.size();
    }

    @Override
    public void removeListDataListener(ListDataListener listener)
    {
        this.listeners.remove(listener);
    }

    @Override
    public Object getSelectedItem()
    {
        return this.selectedItem;
    }

    @Override
    public void setSelectedItem(Object anItem)
    {
        this.selectedItem = anItem;
    }

    @Override
    public void onNodeAdded(Node node)
    {
        if (this.nodeType != null && !node.getClass().equals(this.nodeType))
            return;

        this.nodes.add(node);
        int newIndex = this.nodes.size() - 1;

        for (ListDataListener listener : this.listeners)
            listener.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, newIndex, newIndex));
    }

    @Override
    public void onNodeRemoved(Node node)
    {
        if (this.nodeType != null && !node.getClass().equals(this.nodeType))
            return;

        int oldIndex = this.nodes.indexOf(node);
        this.nodes.remove(node);

        if (this.selectedItem == node)
            this.selectedItem = null;

        for (ListDataListener listener : this.listeners)
            listener.intervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, oldIndex, oldIndex));
    }
}