package Surviv.Scenes.Client;

import Surviv.Entities.Client.Bullet;
import Surviv.Networking.Packets.SpawnBulletPacket;
import Surviv.Networking.Packets.SpawnItemPacket;
import Surviv.Scenes.SurvivMap;
import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.Packet;
import Util.Engine.Scene;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;

public class SurvivGameScene extends Scene implements INetworkListener
{
	public SurvivGameScene()
	{
		super("Surviv.io");

		SurvivMap.generateMap(this);
	}


	@Override
	public void onSceneLoad()
	{
		super.onSceneLoad();

		Engine.netManager().addListener(this);
		Engine.canvas().getFrame().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (packet instanceof SpawnBulletPacket)
		{
			SpawnBulletPacket bulletPacket = (SpawnBulletPacket)packet;

			addEntity(new Bullet(this, bulletPacket.origin, bulletPacket.dir, bulletPacket.dist));
		}
		else if (packet instanceof SpawnItemPacket)
		{
			SpawnItemPacket itemPacket = (SpawnItemPacket)packet;

			try
			{
				addEntity(((SurvivEngineConfiguration) Engine.config()).REGISTERED_ITEMS.get(itemPacket.itemIndex).getConstructor(Scene.class, Vec2f.class).newInstance(this, new Vec2f(itemPacket.x, itemPacket.y)));
			}
			catch (Exception e)
			{
				System.err.println("Unable to spawn entity " + ((SurvivEngineConfiguration) Engine.config()).REGISTERED_ITEMS.get(itemPacket.itemIndex).getName());
			}
		}
	}

}
