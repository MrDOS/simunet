package ca.acadiau.comp4343.simunet.gui;

import java.awt.Dimension;

import javax.swing.JTextField;

public class RecommendedSizeTextField extends JTextField
{
    private static final long serialVersionUID = 1L;

    @Override
    public Dimension getMaximumSize()
    {
        Dimension maximumSize = super.getMaximumSize();
        maximumSize.height = this.getPreferredSize().height;
        return maximumSize;
    }
}