package ca.acadiau.comp4343.simunet.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.Timer;

import ca.acadiau.comp4343.simunet.network.Connection;
import ca.acadiau.comp4343.simunet.network.Network;
import ca.acadiau.comp4343.simunet.network.Node;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

/**
 * Node manipulation toolbar.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class NetworkToolBar extends JToolBar
{
    private static final long serialVersionUID = 1L;

    public NetworkToolBar(final Network network, final NetworkViewer viewer,
            final EditingModalGraphMouse<Node, Connection> graphMouse)
    {
        super();

        final JToggleButton addNodesButton = new JToggleButton("Add/Connect Nodes");
        final JToggleButton modifyNodesButton = new JToggleButton("Modify Nodes");
        final JButton tickButton = new JButton("Tick");
        final JToggleButton autoTickButton = new JToggleButton("Auto-Tick");

        addNodesButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    modifyNodesButton.setSelected(false);
                    graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
                }
                else if (!modifyNodesButton.isSelected())
                {
                    graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
                }
            }
        });
        this.add(addNodesButton);

        modifyNodesButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    addNodesButton.setSelected(false);
                    graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
                }
                else if (!addNodesButton.isSelected())
                {
                    graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
                }
            }
        });
        this.add(modifyNodesButton);

        this.addSeparator();

        tickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                network.tick();
                viewer.repaint();
            }
        });
        this.add(tickButton);

        final Timer tickTimer = new Timer(SimunetFrame.TICK_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                network.tick();
                viewer.repaint();
            }
        });

        autoTickButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                tickButton.setEnabled(!(e.getStateChange() == ItemEvent.SELECTED));

                if (e.getStateChange() == ItemEvent.SELECTED)
                    tickTimer.start();
                else
                    tickTimer.stop();
            }
        });
        this.add(autoTickButton);
    }
}