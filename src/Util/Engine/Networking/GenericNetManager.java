package Util.Engine.Networking;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class GenericNetManager implements INetworkListener
{
	protected EndPoint transport;

	protected List<INetworkListener> listeners;
	protected HashMap<Short, NetGameEntity> netEntities;


	public GenericNetManager(EndPoint transportType, Collection<Class<? extends Packet>> registeredPackets)
	{
		// Transport
		this.transport = transportType;

		// Packets
		for (Class c : registeredPackets)
		{
			this.transport.getKryo().register(c);
		}

		// Register additional classes
		this.transport.getKryo().register(short[].class);

		// Listeners
		listeners = new ArrayList<>();

		transport.addListener(new Listener()
		{
			@Override
			public void received(Connection connection, Object o)
			{
				if (o instanceof Packet)
				{
					// Best alternative to c# delegates which I could come up with
					// It uses toArray to avoid concurrent modification exceptions
					for (INetworkListener listener : listeners.toArray(new INetworkListener[listeners.size()]))
					{
						listener.onReceivePacket(connection, (Packet) o);
					}
				}
			}
		});

		// Listen for packets
		addListener(this);

		// Net Entities
		netEntities = new HashMap<>();
	}


	public void addListener(INetworkListener listener)
	{
		this.listeners.add(listener);
	}


	public void removeListener(INetworkListener listener)
	{
		this.listeners.remove(listener);
	}


	public void addNetEntity(Short networkId, NetGameEntity entity)
	{
		this.netEntities.put(networkId, entity);
	}
}
