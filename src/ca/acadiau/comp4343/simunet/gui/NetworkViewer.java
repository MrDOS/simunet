package ca.acadiau.comp4343.simunet.gui;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import ca.acadiau.comp4343.simunet.network.Connection;
import ca.acadiau.comp4343.simunet.network.Node;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedInfo;

public class NetworkViewer extends VisualizationViewer<Node, Connection>
{
    private static final long serialVersionUID = 1L;

    public NetworkViewer(Layout<Node, Connection> layout)
    {
        super(layout);

        this.setBackground(Color.WHITE);

        this.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Node>());
        this.getRenderContext().setVertexFillPaintTransformer(new NodePaintTransformer(this.getPickedVertexState()));

//        this.getRenderContext().setEdgeLabelTransformer(new Transformer<Connection, String>() {
//            @Override
//            public String transform(Connection connection)
//            {
//                return String.valueOf(connection.getCost());
//            }
//        });
        this.getRenderContext().setEdgeDrawPaintTransformer(new ConnectionDrawTransformer(this.getPickedEdgeState()));
    }

    private class NodePaintTransformer implements Transformer<Node, Paint>
    {
        private final PickedInfo<Node> pickedInfo;

        public NodePaintTransformer(PickedInfo<Node> pickedInfo)
        {
            super();
            this.pickedInfo = pickedInfo;
        }

        @Override
        public Paint transform(Node node)
        {
            if (this.pickedInfo.isPicked(node))
                return Color.BLUE;

            return (node.getLoad() > 0) ? Color.RED : Color.CYAN;
        }
    }

    private class ConnectionDrawTransformer implements Transformer<Connection, Paint>
    {
        private final PickedInfo<Connection> pickedInfo;

        public ConnectionDrawTransformer(PickedInfo<Connection> pickedInfo)
        {
            super();
            this.pickedInfo = pickedInfo;
        }

        @Override
        public Paint transform(Connection connection)
        {
            if (this.pickedInfo.isPicked(connection))
                return Color.BLUE;
            return (connection.getLoad() > 0) ? Color.RED : Color.BLACK;
        }
    }
}