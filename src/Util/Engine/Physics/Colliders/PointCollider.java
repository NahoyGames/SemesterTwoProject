package Util.Engine.Physics.Colliders;

import Util.Engine.GameEntity;
import Util.Engine.Physics.Collider;

import java.awt.*;


public class PointCollider extends Collider
{
	public PointCollider(boolean isStatic, GameEntity entity)
	{
		super(isStatic, entity);
	}


	@Override
	protected void drawDebug(Graphics2D renderBuffer)
	{
		return;
	}
}
