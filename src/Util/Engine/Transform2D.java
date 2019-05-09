package Util.Engine;

import Util.Math.Vec2f;

public class Transform2D
{
	// Position in world space
	public Vec2f position;

	// Rotation in degrees
	public float rotation;

	// Scale
	public Vec2f scale;


	public Transform2D()
	{
		this.position = new Vec2f();
		this.scale = new Vec2f(1, 1);
	}


	public void translate(Vec2f amount)
	{
		this.position = position.add(amount);
	}


	public void lookAt(Vec2f point) { rotation = getLookAtAngle(point); }


	public float getLookAtAngle(Vec2f point)
	{
		// Form a triangle between point and position and use arctan
		// I used desmos to do the math: https://www.desmos.com/calculator/jk1vkerp19
		return ((float)-Math.toDegrees(Math.atan2((point.y - position.y), (point.x - position.x)))) + 90f;
	}
}
