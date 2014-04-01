package ca.acadiau.comp4343.simunet.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.acadiau.comp4343.simunet.network.Connection;

/**
 * Interface subpanel to modify connection costs.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class ConnectionCostPanel extends JPanel implements ItemListener
{
    private static final long serialVersionUID = 1L;

    private final JTextField weight;
    private final JButton setWeight;

    private Connection selected;

    public ConnectionCostPanel(final NetworkViewer viewer)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(new JLabel("Connection cost:"));

        weight = new RecommendedSizeTextField();
        this.add(weight);

        setWeight = new JButton("Set cost");
        setWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev)
            {
                try
                {
                    int cost = Integer.valueOf(weight.getText());
                    ConnectionCostPanel.this.selected.setCost(cost);
                    viewer.repaint();
                }
                catch (NumberFormatException e)
                {
                    return;
                }
            }
        });
        this.add(setWeight);

        this.deselect();

        viewer.getPickedEdgeState().addItemListener(this);
    }

    private void select(Connection connection)
    {
        this.selected = connection;

        weight.setText(String.valueOf(this.selected.getCost()));
        weight.setEnabled(true);
        setWeight.setEnabled(true);
    }

    private void deselect()
    {
        weight.setText("");
        weight.setEnabled(false);
        setWeight.setEnabled(false);
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getStateChange() == ItemEvent.SELECTED)
            this.select((Connection) e.getItem());
        else if (e.getStateChange() == ItemEvent.DESELECTED)
            this.deselect();
    }
}
