package Surviv;

import Surviv.Entities.Client.Player;
import Surviv.Networking.Packets.ClientLookAtPacket;
import Surviv.Networking.Packets.SpawnBulletPacket;
import Surviv.Networking.Packets.TestRequestPacket;
import Util.Engine.EngineConfiguration;

import java.awt.event.KeyEvent;


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

	public final float MAX_BULLET_DISTANCE = 5000f;


	public SurvivEngineConfiguration()
	{
		super();

		/** REGISTER PACKETS >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_PACKETS.add(TestRequestPacket.class);
		REGISTERED_PACKETS.add(ClientLookAtPacket.class);
		REGISTERED_PACKETS.add(SpawnBulletPacket.class);


		/** REGISTER ENTITIES >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_ENTITIES.add(Player.class);
	}
}
