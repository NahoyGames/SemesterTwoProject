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

		System.out.println("Not yet implemented!");

		return null;
	}


	public static CollisionInfo pointBoxCollision(Vec2f pt, Vec2f rectPos, Vec2f rectHalfSize)
	{
		Vec2f normal = pt.subtract(rectPos).abs().subtract(rectHalfSize).max(Vec2f.zero());
		float distSquared = normal.lengthSquared();
		boolean hasCollision = distSquared <= 0;

		if (hasCollision)
		{
			Vec2f faceNormal;
			if (normal.y > 0) // If y > 0, then the point hit either the top or bottom edge
			{
				if (pt.y > rectPos.y)
				{
					faceNormal = Vec2f.up();
				} else
				{
					faceNormal = Vec2f.up().scale(-1);
				}
			} else
			{
				if (pt.x > rectPos.x)
				{
					faceNormal = Vec2f.right();
				} else
				{
					faceNormal = Vec2f.right().scale(-1);
				}
			}

			return new CollisionInfo(pt, faceNormal, distSquared);
		}

		return null;
	}


	public static CollisionInfo boxCircleCollision(Vec2f rectPos, Vec2f rectHalfSize, Vec2f circOrigin, float rad)
	{
		Vec2f normal = circOrigin.subtract(rectPos);
		float distSquared = normal.abs().subtract(rectHalfSize).max(Vec2f.zero()).lengthSquared();
		boolean hasCollision = distSquared <= rad * rad;

		if (hasCollision)
		{
			return new CollisionInfo(null, normal, distSquared);
		}

		return null;
	}
}
