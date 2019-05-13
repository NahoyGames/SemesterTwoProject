package Util.Engine.Physics.Colliders;

import Util.Engine.GameEntity;
import Util.Engine.Physics.Collider;
import Util.Engine.Physics.CollisionInfo;
import Util.Math.Vec2f;

import java.awt.*;

public class CircleCollider extends Collider
{
	private float radius;


	public CircleCollider(boolean isStatic, GameEntity entity, float radius)
	{
		super(isStatic, entity);
		this.radius = radius;
	}


	public float getRadius()
	{
		return radius * getEntity().transform().scale.x;
	}


	@Override
	public CollisionInfo hasCollisionWith(BoxCollider other)
	{
		// Nearest point to the circle, on the box
		Vec2f nearest = other.getOrigin().add(other.getHalfSize()).min(getOrigin()).max(other.getOrigin().subtract(other.getHalfSize()));

		Vec2f normal = nearest.subtract(getOrigin());
		float dist = normal.length() - getRadius();

		if (dist <= 0)
		{
			return new CollisionInfo(nearest, normal.scale(-1).normalized(), dist);
		}

		return null;
	}


	@Override
	public CollisionInfo hasCollisionWith(CircleCollider other)
	{
		Vec2f normal = getOrigin().subtract(other.getOrigin());
		float dist = normal.length() - (getRadius() + other.getRadius());
		boolean hasCollision = dist <= 0;

		if (hasCollision)
		{
			normal = normal.normalized();

			return new CollisionInfo(null, normal, dist);
		}

		return null;
	}


	@Override
	public CollisionInfo hasCollisionWith(PointCollider other)
	{
		return null;
	}


	@Override
	protected void drawDebug(Graphics2D renderBuffer)
	{
		renderBuffer.drawOval((int)-getRadius(), (int)-getRadius(), (int)getRadius() * 2, (int)getRadius() * 2);
	}


}
