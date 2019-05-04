package Surviv.Behaviors;

import Util.Engine.*;
import Util.Math.Vec2f;

public class TestInputMove extends GameBehavior
{
	public TestInputMove(GameEntity entity)
	{
		super(entity);
	}


	@Override
	public void update()
	{
		float horizontal = Engine.input().getAxis("Horizontal");
		float vertical = Engine.input().getAxis("Vertical");
		float mouseY = Engine.input().getAxis("MouseX");

		entity().transform().translate(new Vec2f(horizontal, vertical).scale(500 * Time.deltaTime()));
		//entity().transform().rotation = mouseY;
	}
}
