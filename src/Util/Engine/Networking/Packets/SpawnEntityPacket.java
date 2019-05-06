package Util.Engine.Networking.Packets;

import Util.Engine.Engine;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;

public class SpawnEntityPacket extends Packet
{
	public short networkId;
	public short type;


	public SpawnEntityPacket() { }


	public SpawnEntityPacket(short networkId, Class<? extends ClientGameEntity> type)
	{
		this.networkId = networkId;
		this.type = (short) Engine.config().REGISTERED_ENTITIES.indexOf(type);

		if (this.type < 0)
		{
			System.err.println("Attempting to spawn a non-registered entity!");
			System.exit(-1);
		}
	}
}
