package Util.Engine.Networking.Server;

import Util.Engine.Networking.GenericNetManager;
import Util.Engine.Networking.Packet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.Collection;
import java.util.Stack;


public class ServerNetManager extends GenericNetManager
{
	private Stack<Short> availableNetworkIDs;


	public ServerNetManager(int tcpPort, int udpPort, Collection<Class<? extends Packet>> registeredPackets)
	{
		// Generic
		super(new Server(), registeredPackets);

		// Init Transport
		try
		{
			transport.start();
			((Server)transport).bind(tcpPort, udpPort);
			System.out.println("Server connected on ports TCP::" + tcpPort + " and UDP::" + udpPort);
		}
		catch (IOException e)
		{
			System.out.println("An error occured while attempting to start the server... " + e);
		}

		// Network Entities
		availableNetworkIDs = new Stack<>();
		for (short i = 0; i < 10000; i++) { availableNetworkIDs.push(i); }
	}


	public void sendReliable(Connection connection, Packet packet)
	{
		if (this.transport == null)
		{
			System.out.println("Cannot send a message because the transport is missing or is not connected!");
			return;
		}

		connection.sendTCP(packet);
	}


	public void sendReliable(Packet packet)
	{
		if (this.transport == null)
		{
			System.out.println("Cannot send a message because the transport is missing or is not connected!");
			return;
		}

		((Server)transport).sendToAllTCP(packet);
	}


	public void sendUnreliable(Connection connection, Packet packet)
	{
		if (this.transport == null)
		{
			System.out.println("Cannot send a message because the transport is missing or is not connected!");
			return;
		}

		connection.sendUDP(packet);
	}


	public void sendUnreliable(Packet packet)
	{
		if (this.transport == null)
		{
			System.out.println("Cannot send a message because the transport is missing or is not connected!");
			return;
		}

		((Server)transport).sendToAllUDP(packet);
	}


	public short getNextNetworkId() // Used for spawning Networked GameEntity's
	{
		return availableNetworkIDs.pop();
	}
}
