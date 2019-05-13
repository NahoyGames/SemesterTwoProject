package Util.Engine.Physics.Colliders;

import Util.Engine.GameEntity;
import Util.Engine.Physics.Collider;
import Util.Engine.Physics.CollisionInfo;
import Util.Math.Vec2f;

import java.awt.*;


public class PointCollider extends Collider
{
	public PointCollider(boolean isStatic, GameEntity entity)
	{
		super(isStatic, entity);
	}


	@Override
	public CollisionInfo hasCollisionWith(BoxCollider other)
	{
		Vec2f nearest = other.getOrigin().add(other.getHalfSize()).min(getOrigin()).max(other.getOrigin().subtract(other.getHalfSize()));

		Vec2f normal = nearest.subtract(getOrigin());
		float dist = normal.length();

		if (dist <= 0.001f)
		{
			return new CollisionInfo(nearest, nearest.subtract(other.getOrigin()).normalized(), dist);
		}

		return null;
	}


	@Override
	public CollisionInfo hasCollisionWith(CircleCollider other)
	{
		Vec2f normal = getOrigin().subtract(other.getOrigin());
		float dist = normal.length() - other.getRadius();
		boolean hasCollision = dist <= 0;

		if (hasCollision)
		{
			normal = normal.normalized();
			Vec2f point = normal.scale(other.getRadius()).add(other.getOrigin());

			return new CollisionInfo(point, normal, dist);
		}

		return null;
	}


	@Override
	public CollisionInfo hasCollisionWith(PointCollider other)
	{
		// Not Implemented
		// Also no feasible, point colliders have no volume
		return null;
	}


	@Override
	protected void drawDebug(Graphics2D renderBuffer)
	{
		return;
	}
}
