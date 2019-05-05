package Surviv.Entities;

import Surviv.Behaviors.TestRandomMove;
import Util.Engine.GameEntity;
import Util.Engine.Scene;
import Util.Math.Vec2f;


public class Player extends GameEntity
{
	private static final String SPRITE_PATH = "src/Assets/Sprites/Eye Base Color.png";

	public Player(Scene scene)
	{
		super(scene, SPRITE_PATH);
		transform.scale = new Vec2f(0.1f, 0.1f);
		addBehavior(new TestRandomMove(this));
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
