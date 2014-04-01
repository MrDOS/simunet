package ca.acadiau.comp4343.simunet.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.acadiau.comp4343.simunet.network.Network;
import ca.acadiau.comp4343.simunet.network.Node;
import ca.acadiau.comp4343.simunet.network.Packet;

/**
 * Interface side panel.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class NetworkSidePanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    public NetworkSidePanel(final Network network, final NetworkViewer viewer)
    {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        final JComboBox<Node> sourceNodeComboBox = new RecommendedSizeComboBox<Node>(
                new NetworkComboBoxModel(network));
        final JComboBox<Node> destinationNodeComboBox = new RecommendedSizeComboBox<Node>(
                new NetworkComboBoxModel(network));
        final JButton sendButton = new JButton("Send");

        this.add(new JLabel("Send packet from:"));
        this.add(sourceNodeComboBox);
        this.add(new JLabel("To:"));
        this.add(destinationNodeComboBox);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (sourceNodeComboBox.getSelectedItem() == null
                        || destinationNodeComboBox.getSelectedItem() == null)
                    return;

                ((Node) sourceNodeComboBox.getSelectedItem())
                        .receive(new Packet((Node) destinationNodeComboBox.getSelectedItem()));
                viewer.repaint();
            }
        });
        this.add(sendButton);

        this.add(new RecommendedSizeSeparator());

        this.add(new ConnectionCostPanel(viewer));
    }
}