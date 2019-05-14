package Surviv.Networking.Packets;


import Util.Engine.Networking.Packet;


public class PlayerChangeInventoryPacket extends Packet
{
	public short entityNetworkId;
	public byte inventoryIndex;


	public PlayerChangeInventoryPacket() { }


	public PlayerChangeInventoryPacket(short networkId, byte inventoryIndex)
	{
		this.entityNetworkId = networkId;
		this.inventoryIndex = inventoryIndex;
	}
}
