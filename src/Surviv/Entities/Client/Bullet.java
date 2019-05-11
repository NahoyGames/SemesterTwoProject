package Surviv.Entities.Client;

import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Scene;
import Util.Engine.Time;
import Util.Math.Compressor;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

public class Bullet extends ClientGameEntity
{
	private static String SPRITE_PATH = "/Assets/Sprites/Environment/Bullets/bullet_short_trail.png";


	private Vec2f origin, dir;


	public Bullet(Scene scene, Vec2f origin, byte dir)
	{
		super(scene, SPRITE_PATH, (short)-1);

		anchor = new Vec2f(0.5f, 0.05f);

		this.origin = this.transform.position = origin.clone();

		this.transform.rotation = Compressor.scaleByteToFloat(dir, 0, 360);

		float radDir = (float)Math.toRadians(this.transform.rotation + 90f);
		this.dir = new Vec2f(-(float)Math.cos(radDir), (float)Math.sin(radDir));

		this.transform.scale = new Vec2f(0.15f, 0f);
	}


	@Override
	public void update()
	{
		super.update();

		this.transform.position = this.transform.position.add(dir.scale(Time.deltaTime()));

		float distTraveled = Vec2f.distanceSquared(transform.position, origin);

		this.transform.scale.y = Math.min(0.5f, distTraveled / 50000);

		if (distTraveled >= 1000 * 1000)
		{
			scene.removeEntity(this);
		}
	}

	@Override
	public int getLayer()
	{
		return -1;
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet) { }
}
