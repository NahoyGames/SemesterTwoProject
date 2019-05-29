package Util.Engine.Networking.Server;

import Util.Engine.Engine;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.NetGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.DestroyEntityPacket;
import Util.Engine.Networking.Packets.SpawnEntityPacket;
import Util.Engine.Scene;
import com.esotericsoftware.kryonet.Connection;

public abstract class ServerGameEntity extends NetGameEntity
{
	public ServerGameEntity(Scene scene, String spritePath, Object... spawnParams)
	{
		super(scene, spritePath, ((ServerNetManager) Engine.netManager()).getNextNetworkId());

		if (!(Engine.netManager() instanceof ServerNetManager))
		{
			System.err.println("Critical error: Attempting to spawn a SERVER entity on a CLIENT build!");
			System.exit(-1);
		}

		// Spawn over the network
		spawnOverNetwork(spawnParams);
	}


	public abstract Class<? extends ClientGameEntity> getClientCounterpart();


	protected void spawnOverNetwork(Object... params)
	{
		sendReliable(new SpawnEntityPacket(getNetworkId(), getClientCounterpart()));
	}


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


	@Override
	public void onDisable()
	{
		super.onDisable();

		sendReliable(new DestroyEntityPacket(getNetworkId()));
	}
}
