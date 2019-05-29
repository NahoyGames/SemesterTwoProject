package Surviv.Entities.Server;

import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Server.ServerGameEntity;
import Util.Engine.Networking.Server.ServerNetTransform;
import Util.Engine.Physics.Colliders.CircleCollider;
import Util.Engine.Scene;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

public class AmmoItem extends ServerGameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/LootItems/ammo.png";
	private static final float PICKUP_RADIUS = 50f;


	public AmmoItem(Scene scene, Vec2f position)
	{
		super(scene, SPRITE_PATH);

		// Transform
		this.transform().position = position.clone();
		this.transform().scale = Vec2f.one().scale(0.3f);

		// Behaviors
		addBehavior(new ServerNetTransform(this, 1));
		addBehavior(new CircleCollider(false, this, PICKUP_RADIUS));
	}


	@Override
	public Class<? extends ClientGameEntity> getClientCounterpart()
	{
		return Surviv.Entities.Client.AmmoItem.class;
	}

	@Override
	public int getLayer()
	{
		return 0;
	}

	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{

	}
}
