package Surviv;

import Surviv.Entities.Client.*;
import Surviv.Entities.Items.Ak47Item;
import Surviv.Entities.Items.LootItem;
import Surviv.Networking.Packets.*;
import Util.Engine.EngineConfiguration;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class SurvivEngineConfiguration extends EngineConfiguration
{
	public int MOVE_UP_KEY = KeyEvent.VK_UP;
	public int MOVE_DOWN_KEY = KeyEvent.VK_DOWN;
	public int MOVE_RIGHT_KEY = KeyEvent.VK_RIGHT;
	public int MOVE_LEFT_KEY = KeyEvent.VK_LEFT;

	public int EQUIP_1_KEY = KeyEvent.VK_1;
	public int EQUIP_2_KEY = KeyEvent.VK_2;
	public int EQUIP_3_KEY = KeyEvent.VK_3;
	public int EQUIP_4_KEY = KeyEvent.VK_4;
	public int PICK_UP_KEY = KeyEvent.VK_F;

	public final float MAX_BULLET_DISTANCE = 5000f;

	public final List<Class<? extends LootItem>> REGISTERED_ITEMS = new ArrayList<>();

	public SurvivEngineConfiguration()
	{
		super();

		/** REGISTER PACKETS >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_PACKETS.add(TestRequestPacket.class);
		REGISTERED_PACKETS.add(ClientLookAtPacket.class);
		REGISTERED_PACKETS.add(SpawnBulletPacket.class);
		REGISTERED_PACKETS.add(PlayerChangeInventoryPacket.class);
		REGISTERED_PACKETS.add(HealthPacket.class);
		REGISTERED_PACKETS.add(SpawnItemPacket.class);


		/** REGISTER ENTITIES >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_ENTITIES.add(Player.class);
		REGISTERED_ENTITIES.add(LootCrate.class);
		REGISTERED_ENTITIES.add(Tree.class);


		/** REGISTER LOOT ITEMS >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_ITEMS.add(Ak47Item.class);
	}
}
