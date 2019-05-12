package Util.Engine.Physics.Colliders;

import Util.Engine.GameEntity;
import Util.Engine.Physics.Collider;

import java.awt.*;

public class CircleCollider extends Collider
{
	private float radius;


	public CircleCollider(boolean isStatic, GameEntity entity, float radius)
	{
		super(isStatic, entity);
		this.radius = radius;
	}


	@Override
	protected void drawDebug(Graphics2D renderBuffer)
	{
		renderBuffer.drawOval((int)-radius, (int)-radius, (int)radius * 2, (int)radius * 2);
	}


	public float getRadius()
	{
		return radius;
	}
}
