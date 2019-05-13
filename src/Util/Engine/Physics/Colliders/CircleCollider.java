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
		renderBuffer.drawOval((int)-getRadius(), (int)-getRadius(), (int)getRadius() * 2, (int)getRadius() * 2);
	}


	public float getRadius()
	{
		return radius * getEntity().transform().scale.x;
	}
}
