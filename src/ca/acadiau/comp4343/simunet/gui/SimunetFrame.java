package ca.acadiau.comp4343.simunet.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import ca.acadiau.comp4343.simunet.network.Connection;
import ca.acadiau.comp4343.simunet.network.ConnectionFactory;
import ca.acadiau.comp4343.simunet.network.Network;
import ca.acadiau.comp4343.simunet.network.Node;
import ca.acadiau.comp4343.simunet.network.NodeFactory;
import ca.acadiau.comp4343.simunet.network.routing.FloodRoutingStrategy;
import ca.acadiau.comp4343.simunet.network.routing.RandomRoutingStrategy;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * Top-level GUI element.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class SimunetFrame extends JFrame
{
    public static final int TICK_TIME = 500;

    private static final long serialVersionUID = 1L;

    private static final String NODE_DELETION_ACTION_KEY = "Delete Node";

    public SimunetFrame(final Network network)
    {
        Layout<Node, Connection> layout = new StaticLayout<Node, Connection>(network);
        NetworkViewer viewer = new NetworkViewer(layout);

        viewer.getActionMap().put(SimunetFrame.NODE_DELETION_ACTION_KEY, new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                NetworkViewer viewer = (NetworkViewer) e.getSource();
                PickedState<Node> pickedNodes = viewer.getPickedVertexState();
                PickedState<Connection> pickedConnections = viewer.getPickedEdgeState();

                for (Node node : pickedNodes.getPicked())
                    network.removeVertex(node);
                pickedNodes.clear();

                for (Connection connection : pickedConnections.getPicked())
                    network.removeEdge(connection);
                pickedConnections.clear();

                SimunetFrame.this.repaint();
            }
        });
        viewer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), SimunetFrame.NODE_DELETION_ACTION_KEY);

        final EditingModalGraphMouse<Node, Connection> graphMouse = new EditingModalGraphMouse<Node, Connection>(
                viewer.getRenderContext(), NodeFactory.getInstance(), ConnectionFactory.getInstance());
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        viewer.setGraphMouse(graphMouse);

        JToolBar toolBar = new NetworkToolBar(network, viewer, graphMouse);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(viewer, BorderLayout.CENTER);
        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        this.getContentPane().add(new NetworkSidePanel(network, viewer), BorderLayout.EAST);
        this.pack();

        JMenuBar menuBar = new JMenuBar();

        JMenu networkMenu = new JMenu("Network");
        menuBar.add(networkMenu);

        ButtonGroup routingStrategyGroup = new ButtonGroup();

        JRadioButtonMenuItem randomMenuItem = new JRadioButtonMenuItem("Random routing strategy");
        randomMenuItem.setSelected(true);
        randomMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                network.setStrategy(new RandomRoutingStrategy());
            }
        });
        routingStrategyGroup.add(randomMenuItem);
        networkMenu.add(randomMenuItem);

        JRadioButtonMenuItem floodMenuItem = new JRadioButtonMenuItem("Flood routing strategy");
        floodMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                network.setStrategy(new FloodRoutingStrategy());
            }
        });
        routingStrategyGroup.add(floodMenuItem);
        networkMenu.add(floodMenuItem);

        this.setJMenuBar(menuBar);
    }
}