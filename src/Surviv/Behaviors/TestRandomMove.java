package Surviv.Behaviors;

import Util.Engine.EngineEventListener;
import Util.Engine.GameEntity;
import Util.Math.Vec2f;

public class TestRandomMove extends EngineEventListener
{
	public TestRandomMove(GameEntity entity)
	{
		super(entity);
	}

	@Override
	public void update()
	{
		entity().transform().scale = new Vec2f(0.1f, 0.1f);
		entity().transform().rotation += 5;
		//entity().transform().translate(Vec2f.rand(1.3f));
	}
}
