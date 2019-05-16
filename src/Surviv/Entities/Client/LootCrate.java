package Surviv.Entities.Client;

import Surviv.Behaviors.Health.ClientHealthBehavior;
import Surviv.Entities.Environment.IEnvironment;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Client.ClientNetTransform;
import Util.Engine.Networking.Packet;
import Util.Engine.Physics.Colliders.BoxCollider;
import Util.Engine.Scene;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;


public class LootCrate extends ClientGameEntity implements IEnvironment
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Environment/crate.png";

	private ClientHealthBehavior health;


	public LootCrate(Scene scene, short networkId)
	{
		super(scene, SPRITE_PATH, networkId);

		// Transform
		transform().scale = Vec2f.one().scale(0.15f);

		// Collider
		addBehavior(new BoxCollider(true, this, Vec2f.one().scale(150f)));

		// Behaviors
		addBehavior(health = new ClientHealthBehavior(this, 50));
		addBehavior(new ClientNetTransform(this));
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
	public void onReceivePacket(Connection sender, Packet packet)
	{

	}
}
