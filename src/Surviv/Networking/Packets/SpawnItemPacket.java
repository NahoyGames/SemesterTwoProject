package Surviv.Networking.Packets;

import Surviv.Entities.Items.LootItem;
import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
import Util.Engine.Networking.Packet;
import Util.Math.Vec2f;


public class SpawnItemPacket extends Packet
{
	public short itemIndex; // Index of the item in REGISTERED_ENTITIES
	public float x, y; // Position


	public SpawnItemPacket() { }


	public SpawnItemPacket(LootItem item, Vec2f position)
	{
		this.itemIndex = (short)((SurvivEngineConfiguration) Engine.config()).REGISTERED_ITEMS.indexOf(item.getClass());
		this.x = position.x;
		this.y = position.y;
	}
}
