package Util.Engine.Networking.Packets;

import Util.Engine.Networking.Packet;

public class ClientAuthResponsePacket extends Packet
{
	public boolean canJoin;


	public ClientAuthResponsePacket() { }


	public ClientAuthResponsePacket(boolean canJoin)
	{
		this.canJoin = canJoin;
	}
}
