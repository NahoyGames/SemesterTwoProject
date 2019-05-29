package Surviv.Networking.Packets;

import Util.Engine.Networking.Packets.NetInt32Packet;

public class AmmoChangePacket extends NetInt32Packet
{
	public AmmoChangePacket(int value)
	{
		super(value);
	}

	public AmmoChangePacket() { }
}
