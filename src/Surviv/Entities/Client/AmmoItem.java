package Surviv.Entities.Client;

import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Client.ClientNetTransform;
import Util.Engine.Scene;
import Util.Math.Vec2f;

public class AmmoItem extends ClientGameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/LootItems/ammo.png";


	public AmmoItem(Scene scene, short networkId)
	{
		super(scene, SPRITE_PATH, networkId);

		this.transform().scale = Vec2f.one().scale(0.3f);

		// Behaviors
		addBehavior(new ClientNetTransform(this));
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
