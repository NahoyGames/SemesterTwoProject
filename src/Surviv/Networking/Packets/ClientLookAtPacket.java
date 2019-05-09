package Surviv.Networking.Packets;

import Util.Engine.Networking.Packet;
import Util.Math.Compressor;


/**
 * Requests for the character to look at the mouse
 */
public class ClientLookAtPacket extends Packet
{
	public byte rotation;


	public ClientLookAtPacket() { }


	public ClientLookAtPacket(float rotation)
	{
		this.rotation = Compressor.scaleFloatToByte(rotation % 360, -180, 180);
	}
}
