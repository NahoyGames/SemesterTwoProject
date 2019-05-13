package Util.Engine.Physics.Colliders;

import Util.Engine.GameEntity;
import Util.Engine.Physics.Collider;
import Util.Engine.Physics.CollisionInfo;
import Util.Math.Vec2f;

import java.awt.*;

public class BoxCollider extends Collider
{
	private Vec2f halfSize;


	public BoxCollider(boolean isStatic, GameEntity entity, Vec2f halfSize)
	{
		super(isStatic, entity);
		this.halfSize = halfSize;
	}


	public Vec2f getHalfSize()
	{
		return halfSize.scale(getEntity().transform().scale);
	}


	@Override
	public CollisionInfo hasCollisionWith(BoxCollider other)
	{
		// Not implemented
		return null;
	}


	@Override
	public CollisionInfo hasCollisionWith(CircleCollider other)
	{
		// Nearest point to the circle, on the box
		Vec2f nearest = getOrigin().add(getHalfSize()).min(other.getOrigin()).max(getOrigin().subtract(getHalfSize()));

		Vec2f normal = nearest.subtract(other.getOrigin());
		float dist = normal.length() - other.getRadius();

		if (dist <= 0)
		{
			return new CollisionInfo(nearest, normal.scale(-1).normalized(), dist);
		}

		return null;
	}


	@Override
	public CollisionInfo hasCollisionWith(PointCollider other)
	{
		Vec2f nearest = getOrigin().add(getHalfSize()).min(other.getOrigin()).max(getOrigin().subtract(getHalfSize()));

		Vec2f normal = nearest.subtract(other.getOrigin());
		float dist = normal.length();

		if (dist <= 0.001f)
		{
			return new CollisionInfo(nearest, nearest.subtract(getOrigin()).normalized(), dist);
		}

		return null;
	}


	@Override
	protected void drawDebug(Graphics2D renderBuffer)
	{
		renderBuffer.drawRect((int)-getHalfSize().x, (int)-getHalfSize().y, (int)getHalfSize().x * 2, (int)getHalfSize().y * 2);
	}
}
