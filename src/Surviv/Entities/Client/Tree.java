package Surviv.Entities.Client;

import Surviv.Behaviors.Health.ClientHealthBehavior;
import Surviv.Entities.Environment.IEnvironment;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Client.ClientNetTransform;
import Util.Engine.Networking.Packet;
import Util.Engine.Physics.Colliders.BoxCollider;
import Util.Engine.Physics.Colliders.CircleCollider;
import Util.Engine.Scene;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;


public class Tree extends ClientGameEntity implements IEnvironment
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Environment/tree.png";

	private ClientHealthBehavior health;


	public Tree(Scene scene, short networkId)
	{
		super(scene, SPRITE_PATH, networkId);

		// Transform
		transform().scale = Vec2f.one().scale(0.45f);

		// Collider
		addBehavior(new CircleCollider(true, this, 50f));

		// Behaviors
		addBehavior(health = new ClientHealthBehavior(this, 75));
		addBehavior(new ClientNetTransform(this));
	}


	@Override
	public int getLayer()
	{
		return 5;
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
