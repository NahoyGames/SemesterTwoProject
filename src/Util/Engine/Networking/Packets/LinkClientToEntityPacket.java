package Util.Engine.Networking.Packets;

import Util.Engine.Networking.Packet;

public class LinkClientToEntityPacket extends Packet
{
	public short entityNetworkId;


	public LinkClientToEntityPacket() { }


	public LinkClientToEntityPacket(short entityNetworkId)
	{
		this.entityNetworkId = entityNetworkId;
	}
}
