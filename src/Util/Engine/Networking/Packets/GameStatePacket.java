package Util.Engine.Networking.Packets;

import Util.Engine.Engine;
import Util.Engine.Networking.NetGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Server.ServerGameEntity;

public class GameStatePacket extends Packet
{
	// Entities
	public short[] networkIds;
	public short[] entityTypes;


	public GameStatePacket() { }


	public GameStatePacket(ServerGameEntity[] entities)
	{
		this.networkIds = new short[entities.length];
		this.entityTypes = new short[entities.length];

		// Entities
		int i = 0;
		for (ServerGameEntity entity : entities)
		{
			networkIds[i] = entity.getNetworkId();
			entityTypes[i] = (short) Engine.config().REGISTERED_ENTITIES.indexOf(entity.getClientCounterpart());

			if (entityTypes[i] < 0)
			{
				System.err.println("Attempting to spawn a non-registered entity!");
				System.exit(-1);
			}

			i++;
		}
	}
}
