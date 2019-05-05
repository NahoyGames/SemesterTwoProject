package Surviv.Entities;

import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Engine.Scene;
import Util.Engine.Time;
import Util.Math.Vec2f;


public class Player extends GameEntity
{
	private static final String SPRITE_PATH = "src/Assets/Sprites/Eye Base Color.png";

	public Player(Scene scene)
	{
		super(scene, SPRITE_PATH);
		transform.scale = new Vec2f(0.1f, 0.1f);
	}

	@Override
	public int getLayer()
	{
		return 0;
	}


	@Override
	public void update()
	{
		float horizontal = Engine.input().getAxis("Horizontal");
		float vertical = Engine.input().getAxis("Vertical");

		transform.position.x += horizontal * Time.deltaTime();
		transform.position.y += vertical * Time.deltaTime();

		//transform.rotation += Time.deltaTime();
	}
}
