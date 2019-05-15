package Surviv.Networking.Packets;

import Util.Engine.Networking.Packet;
import Util.Math.Compressor;

public class HealthPacket extends Packet
{
	public byte newHealth;
	public short entityNetworkId;


	public HealthPacket() { }


	public HealthPacket(int newHealth, int maxHealth, short entityNetworkId)
	{
		this.newHealth = Compressor.scaleFloatToByte(newHealth, 0, maxHealth);
		this.entityNetworkId = entityNetworkId;
	}
}
