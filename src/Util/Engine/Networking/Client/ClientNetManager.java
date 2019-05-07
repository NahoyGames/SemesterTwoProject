package Util.Engine.Networking.Client;

import Util.Engine.Engine;
import Util.Engine.Networking.GenericNetManager;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.ClientAuthRequestPacket;
import Util.Engine.Networking.Packets.ClientAuthResponsePacket;
import Util.Engine.Networking.Packets.GameStatePacket;
import Util.Engine.Networking.Packets.SpawnEntityPacket;
import Util.Engine.Scene;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;


import java.io.IOException;
import java.util.Collection;

public class ClientNetManager extends GenericNetManager
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

		// Send Auth packet
		sendReliable(new ClientAuthRequestPacket("myUsername"));
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
		if (packet instanceof ClientAuthResponsePacket)
		{
			ClientAuthResponsePacket responsePacket = (ClientAuthResponsePacket)packet;

			System.out.println("Received auth packet...");

			if (!responsePacket.canJoin)
			{
				System.err.println("Could not join because the client is on an outdated version!");
				System.exit(0);
			}
		}
		else if (packet instanceof SpawnEntityPacket)
		{
			SpawnEntityPacket entityPacket = (SpawnEntityPacket)packet;

			try
			{
				Engine.scene().addEntity(Engine.config().REGISTERED_ENTITIES.get(entityPacket.type).getConstructor(Scene.class, short.class).newInstance(Engine.scene(), entityPacket.networkId));
			}
			catch (Exception e)
			{
				System.err.println("Could not summon entity typeof " + Engine.config().REGISTERED_ENTITIES.get(entityPacket.type).getName() + " ... Error: " + e.toString());
			}
		}
		else if (packet instanceof GameStatePacket)
		{
			GameStatePacket statePacket = (GameStatePacket)packet;

			// Entities
			for (int i = 0; i < statePacket.networkIds.length; i++)
			{
				try
				{
					Engine.scene().addEntity(Engine.config().REGISTERED_ENTITIES.get(statePacket.entityTypes[i]).getConstructor(Scene.class, short.class).newInstance(Engine.scene(), statePacket.networkIds[i]));
				}
				catch (Exception e)
				{
					System.err.println("Could not summon entity typeof " + Engine.config().REGISTERED_ENTITIES.get(statePacket.entityTypes[i]).getName() + " ... Error: " + e.toString());
				}
			}
		}
	}
}
