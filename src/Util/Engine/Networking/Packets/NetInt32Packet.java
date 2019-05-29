package Util.Engine.Networking.Packets;


import Util.Engine.Networking.Packet;

public abstract class NetInt32Packet extends Packet
{
	public int value;


	public NetInt32Packet() { }

	public NetInt32Packet(int value)
	{
		this.value = value;
	}
}
