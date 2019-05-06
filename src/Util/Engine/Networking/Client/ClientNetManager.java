package Util.Engine.Networking.Client;

import Util.Engine.Engine;
import Util.Engine.Networking.GenericNetManager;
import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.SpawnEntityPacket;
import Util.Engine.Scene;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;


import java.io.IOException;
import java.util.Collection;

public class ClientNetManager extends GenericNetManager implements INetworkListener
{
	public ClientNetManager(String ip, int tcpPort, int udpPort, Collection<Class<? extends Packet>> registeredPackets)
	{
		// Generic
		super(new Client(), registeredPackets);

		// Init Transport
		try
		{
			transport.start();
			((Client)transport).connect(5000, ip, tcpPort, udpPort);
		}
		catch (IOException e)
		{
			System.out.println("An error occured while attempting to connect the client... " + e);
		}

		// Listen for packets
		addListener(this);
	}


	public void sendReliable(Packet packet)
	{
		if (this.transport == null)
		{
			System.out.println("Cannot send a message because the transport is missing or is not connected!");
			return;
		}

		((Client)transport).sendTCP(packet);
	}


	public void sendUnreliable(Packet packet)
	{
		if (this.transport == null)
		{
			System.out.println("Cannot send a message because the transport is missing or is not connected!");
			return;
		}

		((Client)transport).sendUDP(packet);
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (packet instanceof SpawnEntityPacket)
		{
			SpawnEntityPacket entityPacket = (SpawnEntityPacket)packet;

			try
			{
				System.out.println("Spawned an entity on server!");
				Engine.scene().addEntity(Engine.config().REGISTERED_ENTITIES.get(entityPacket.type).getConstructor(Scene.class, short.class).newInstance(Engine.scene(), entityPacket.networkId));
			}
			catch (Exception e)
			{
				System.err.println("Could not summon entity typeof " + Engine.config().REGISTERED_ENTITIES.get(entityPacket.type).getName() + " ... Error: " + e.toString());
			}
		}
	}
}
