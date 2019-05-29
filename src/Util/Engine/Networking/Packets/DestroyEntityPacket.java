package Util.Engine.Networking.Packets;

import Util.Engine.Networking.Packet;

public class DestroyEntityPacket extends Packet
{
	public short networkId;


	public DestroyEntityPacket() { }


	public DestroyEntityPacket(short networkId)
	{
		this.networkId = networkId;
	}
}
