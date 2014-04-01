package ca.acadiau.comp4343.simunet.network;

import org.apache.commons.collections15.Factory;

/**
 * Produces {@link Connections}.
 * 
 * @author Samuel Coleman <105709c@acadiau.ca>
 */
public class ConnectionFactory implements Factory<Connection>
{
    private static ConnectionFactory instance;

    private ConnectionFactory()
    {
    }

    public static ConnectionFactory getInstance()
    {
        if (ConnectionFactory.instance == null)
            ConnectionFactory.instance = new ConnectionFactory();
        return ConnectionFactory.instance;
    }

    @Override
    public Connection create()
    {
        return new Connection();
    }
}