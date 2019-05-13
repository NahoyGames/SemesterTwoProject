package Util.Engine.Physics;

import Util.Math.Vec2f;

public class CollisionInfo
{
	public final Vec2f point; // Point at which the intersection happened
	public final Vec2f normal; // Normal of the collider/between the two
	public final float dist; // Distance the colliders are intersecting


	public CollisionInfo(Vec2f point, Vec2f normal, float dist)
	{
		this.point = point;
		this.normal = normal;
		this.dist = dist;
	}
}
