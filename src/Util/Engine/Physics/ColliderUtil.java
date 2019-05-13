package Util.Engine.Physics;

import Util.Math.Vec2f;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


// Collisions based of SDFs from:
// https://iquilezles.org/www/articles/distfunctions2d/distfunctions2d.htm
public class ColliderUtil
{
	public static CollisionInfo pointCircleCollision(Vec2f pt, Vec2f circOrigin, float rad)
	{
		Vec2f normal = pt.subtract(circOrigin);
		float dist = normal.length() - rad;
		boolean hasCollision = dist <= 0;

		if (hasCollision)
		{
			normal = normal.normalized();
			Vec2f point = normal.scale(rad).add(circOrigin);

			return new CollisionInfo(point, normal, dist);
		}

		return null;
	}


	public static CollisionInfo circleCircleCollision(Vec2f aOrigin, float aRad, Vec2f bOrigin, float bRad)
	{
		Vec2f normal = aOrigin.subtract(bOrigin);
		float dist = normal.length() - (aRad + bRad);
		boolean hasCollision = dist <= 0;

		if (hasCollision)
		{
			normal = normal.normalized();

			return new CollisionInfo(null, normal, dist);
		}

		return null;
	}


	public static CollisionInfo pointBoxCollision(Vec2f pt, Vec2f rectPos, Vec2f rectHalfSize)
	{
		Vec2f nearest = rectPos.add(rectHalfSize).min(pt).max(rectPos.subtract(rectHalfSize));

		Vec2f normal = nearest.subtract(pt);
		float dist = normal.length();

		if (dist <= 0.001f)
		{
			return new CollisionInfo(nearest, nearest.subtract(rectPos).normalized(), dist);
		}

		return null;
	}


	public static CollisionInfo boxCircleCollision(Vec2f rectPos, Vec2f rectHalfSize, Vec2f circOrigin, float rad)
	{
		// Nearest point to the circle, on the box
		Vec2f nearest = rectPos.add(rectHalfSize).min(circOrigin).max(rectPos.subtract(rectHalfSize));

		Vec2f normal = nearest.subtract(circOrigin);
		float dist = normal.length() - rad;

		if (dist <= 0)
		{
			return new CollisionInfo(nearest, normal.scale(-1).normalized(), dist);
		}

		return null;
	}
}
