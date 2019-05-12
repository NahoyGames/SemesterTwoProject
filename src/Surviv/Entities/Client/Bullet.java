package Surviv.Entities.Client;

import Surviv.Behaviors.FadeBehavior;
import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Physics.Collider;
import Util.Engine.Physics.Colliders.PointCollider;
import Util.Engine.Physics.CollisionInfo;
import Util.Engine.Scene;
import Util.Engine.Time;
import Util.Math.Compressor;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

public class Bullet extends ClientGameEntity
{
	private static String SPRITE_PATH = "/Assets/Sprites/Environment/Bullets/bullet_short_trail.png";


	private Vec2f origin, dir;
	private float distSquared;

	private FadeBehavior fadeBehavior;

	private PointCollider collider;


	public Bullet(Scene scene, Vec2f origin, byte dir, byte dist)
	{
		super(scene, SPRITE_PATH, (short)-1);

		anchor = new Vec2f(0.5f, 0.05f);

		this.origin = this.transform.position = origin.clone();

		this.transform.rotation = Compressor.scaleByteToFloat(dir, 0, 360);

		float radDir = (float)Math.toRadians(this.transform.rotation + 90f);
		this.dir = new Vec2f(-(float)Math.cos(radDir), (float)Math.sin(radDir));

		distSquared = Compressor.scaleByteToFloat(dist, 0, ((SurvivEngineConfiguration) Engine.config()).MAX_BULLET_DISTANCE);
		distSquared *= distSquared;

		this.transform.scale = new Vec2f(0.15f, 0f);

		fadeBehavior = ((FadeBehavior)addBehavior(new FadeBehavior(this)));

		scene.collisionManager().addCollider(collider = new PointCollider(false, this)
		{
			@Override
			public void onCollision(Collider other, CollisionInfo info)
			{
				super.onCollision(other, info);

				//fadeBehavior.scheduleFade(0.01f, 0f, true);

				// Bounce
				Bullet.this.origin = info.point;
				Bullet.this.dir = info.normal;
				Bullet.this.transform.rotation = (float)Math.toDegrees(Math.atan2(info.normal.y, -info.normal.x)) - 90f;
				Bullet.this.transform.scale.y = 0f;
				Bullet.this.distSquared /= 10;
			}
		});
	}


	@Override
	public void update()
	{
		super.update();

		this.transform.position = this.transform.position.add(dir.scale(Time.deltaTime()));

		float distTraveled = Vec2f.distanceSquared(transform.position, origin);

		this.transform.scale.y = Math.min(0.5f, distTraveled / 50000);

		if (distTraveled >= distSquared)
		{
			fadeBehavior.scheduleFade(0.05f, 0f, true);
		}
	}

	@Override
	public int getLayer()
	{
		return -1;
	}


	@Override
	public void onDisable()
	{
		super.onDisable();

		scene.collisionManager().removeCollider(collider);
	}

	@Override
	public void onReceivePacket(Connection sender, Packet packet) { }
}
