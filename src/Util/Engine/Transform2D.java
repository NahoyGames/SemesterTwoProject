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
}
