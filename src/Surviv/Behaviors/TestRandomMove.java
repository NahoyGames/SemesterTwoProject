package Surviv.Behaviors;

import Util.Engine.GameBehavior;
import Util.Engine.GameEntity;
import Util.Math.Vec2f;

public class TestRandomMove extends GameBehavior
{
	public TestRandomMove(GameEntity entity)
	{
		super(entity);
	}

	@Override
	public void update()
	{
		entity().transform().scale = new Vec2f(0.1f, 0.1f);
		entity().transform().rotation += Math.random();
		//entity().transform().translate(Vec2f.rand(1.3f));
	}
}
