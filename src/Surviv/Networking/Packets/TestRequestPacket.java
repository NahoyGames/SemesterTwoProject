package Surviv.Networking.Packets;

import Util.Engine.Networking.Packet;

public class TestRequestPacket extends Packet
{
	public String message;

	public TestRequestPacket()
	{
		this.message = "";
	}

	public TestRequestPacket(String message)
	{
		this.message = message;
	}
}
