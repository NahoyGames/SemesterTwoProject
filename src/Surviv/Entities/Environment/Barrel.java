package Surviv.Entities.Environment;

import Util.Engine.GameEntity;
import Util.Engine.Physics.Colliders.CircleCollider;
import Util.Engine.Scene;


public class Barrel extends GameEntity implements IBouncyEnvironment
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Environment/barrel.png";


	public Barrel(Scene scene)
	{
		super(scene, SPRITE_PATH);

		addBehavior(new CircleCollider(true, this, 50f));
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
