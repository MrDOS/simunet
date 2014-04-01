package ca.acadiau.comp4343.simunet.gui;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class RecommendedSizeComboBox<E> extends JComboBox<E>
{
    private static final long serialVersionUID = 1L;

    public RecommendedSizeComboBox()
    {
        super();
    }

    public RecommendedSizeComboBox(ComboBoxModel<E> aModel)
    {
        super(aModel);
    }

    public RecommendedSizeComboBox(E[] items)
    {
        super(items);
    }

    public RecommendedSizeComboBox(Vector<E> items)
    {
        super(items);
    }

    @Override
    public Dimension getMaximumSize()
    {
        Dimension maximumSize = super.getMaximumSize();
        maximumSize.height = this.getPreferredSize().height;
        return maximumSize;
    }
}
