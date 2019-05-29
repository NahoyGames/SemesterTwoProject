package Surviv.Entities.Server;

import Surviv.Behaviors.Health.ServerHealthBehavior;
import Surviv.Entities.Environment.IEnvironment;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Server.ServerGameEntity;
import Util.Engine.Networking.Server.ServerNetTransform;
import Util.Engine.Physics.Colliders.BoxCollider;
import Util.Engine.Scene;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;


public class LootCrate extends ServerGameEntity implements IEnvironment
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Environment/crate.png";

	private ServerHealthBehavior health;


	public LootCrate(Scene scene)
	{
		super(scene, SPRITE_PATH);

		// Transform
		transform().scale = Vec2f.one().scale(0.15f);

		// Collider
		addBehavior(new BoxCollider(true, this, Vec2f.one().scale(150f)));

		// Behaviors
		addBehavior(health = new ServerHealthBehavior(this, 50));
		addBehavior(new ServerNetTransform(this, 1));
	}


	@Override
	public int getLayer()
	{
		return 0;
	}


	@Override
	protected void drawGui(Graphics2D renderBuffer)
	{
		super.drawGui(renderBuffer);

		health.drawHealthBar(renderBuffer, -30);
	}


	@Override
	public Class<? extends ClientGameEntity> getClientCounterpart()
	{
		return Surviv.Entities.Client.LootCrate.class;
	}

	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{

	}


	@Override
	public void onDisable()
	{
		super.onDisable();

		//scene.addEntity(new AmmoItem(scene, transform.position));
		scene.addEntity(new AmmoItem(scene, transform.position));
	}
}
