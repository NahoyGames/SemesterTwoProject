package Surviv.Entities.Items;

import Surviv.Networking.Packets.SpawnItemPacket;
import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Engine.Networking.Server.ServerNetManager;
import Util.Engine.Physics.Colliders.CircleCollider;
import Util.Engine.Scene;
import Util.Math.Vec2f;


public abstract class LootItem extends GameEntity
{
	private static final float PICK_UP_RADIUS = 5f;


	public LootItem(Scene scene, Vec2f position, String spritePath, Vec2f anchor)
	{
		super(scene, spritePath, anchor);

		transform.position = position.clone();
		transform.scale = Vec2f.one().scale(0.15f);

		addBehavior(new CircleCollider(true, this, PICK_UP_RADIUS));

		// Spawn over network
		if (Engine.config().IS_SERVER_BUILD)
		{
			((ServerNetManager)Engine.netManager()).sendReliable(new SpawnItemPacket(this, position));
		}
	}


	public LootItem(Scene scene, Vec2f position, String spritePath)
	{
		this(scene, position, spritePath, Vec2f.one().scale(0.5f));
	}


	public abstract String getName();


	public abstract String getDescription();


	@Override
	public int getLayer()
	{
		return 2;
	}
}
