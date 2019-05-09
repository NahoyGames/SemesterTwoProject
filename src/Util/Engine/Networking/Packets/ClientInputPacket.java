package Util.Engine.Networking.Packets;

import Util.Engine.Networking.Packet;

import java.awt.event.KeyEvent;

public class ClientInputPacket extends Packet
{
	public int keyCode;
	public boolean setOn;


	public ClientInputPacket() { }


	public ClientInputPacket(int keyCode, boolean setOn)
	{
		this.keyCode = keyCode;
		this.setOn = setOn;
	}
}
