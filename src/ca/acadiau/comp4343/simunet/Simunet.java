package ca.acadiau.comp4343.simunet;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ca.acadiau.comp4343.simunet.gui.SimunetFrame;
import ca.acadiau.comp4343.simunet.network.Network;
import ca.acadiau.comp4343.simunet.network.routing.RandomRoutingStrategy;

/**
 * Simunet entry point.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class Simunet
{
    public static void main(String[] args)
    {
        Network network = new Network();
        network.setStrategy(new RandomRoutingStrategy());

        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
        {
            if (info.getName().equals("Nimbus"))
            {
                try
                {
                    UIManager.setLookAndFeel(info.getClassName());
                }
                catch (Exception e)
                {
                }
                break;
            }
        }

        SimunetFrame frame = new SimunetFrame(network);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}