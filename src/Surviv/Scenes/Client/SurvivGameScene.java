package Surviv.Scenes.Client;

import Surviv.Entities.Client.Bullet;
import Surviv.Networking.Packets.SpawnBulletPacket;
import Surviv.Scenes.SurvivMap;
import Util.Engine.Engine;
import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.Packet;
import Util.Engine.Scene;
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
	}

}
