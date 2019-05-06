package Util.Engine.Networking.Server;

import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.SpawnEntityPacket;
import Util.Engine.Scene;
import com.esotericsoftware.kryonet.Connection;

public abstract class ServerGameEntity extends GameEntity implements INetworkListener
{
	private short networkId;


	public ServerGameEntity(Scene scene, String spritePath)
	{
		super(scene, spritePath);

		if (!(Engine.netManager() instanceof ServerNetManager))
		{
			System.err.println("Critical error: Attempting to spawn a SERVER entity on a CLIENT build!");
			System.exit(-1);
		}

		Engine.netManager().addListener(this);

		// Spawn over the network
		sendReliable(new SpawnEntityPacket(networkId = ((ServerNetManager) Engine.netManager()).getNextNetworkId(), getClientCounterpart()));
	}


	public abstract Class<? extends ClientGameEntity> getClientCounterpart();


	protected final void sendReliable(Packet packet)
	{
		((ServerNetManager)Engine.netManager()).sendReliable(packet);
	}


	protected final void sendReliable(Connection connection, Packet packet)
	{
		((ServerNetManager)Engine.netManager()).sendReliable(connection, packet);
	}


	protected final void sendUnreliable(Packet packet)
	{
		((ServerNetManager)Engine.netManager()).sendUnreliable(packet);
	}


	protected final void sendUnreliable(Connection connection, Packet packet)
	{
		((ServerNetManager)Engine.netManager()).sendUnreliable(connection, packet);
	}
}
