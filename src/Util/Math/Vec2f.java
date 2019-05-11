package Util.Math;


// Designed to be immutable
public class Vec2f
{
	public float x;
	public float y;


	public Vec2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vec2f() { this(0, 0); }

	public static Vec2f one() { return new Vec2f(1, 1); }
	public static Vec2f zero() { return new Vec2f(0, 0); }
	public static Vec2f up() { return new Vec2f(0, 1); }
	public static Vec2f right() { return new Vec2f(1, 0); }
	public static Vec2f rand(float length) { return (new Vec2f((float)(Math.random() - 0.5), (float)(Math.random() - 0.5))).normalized().scale(length); }


	public static Vec2f add(Vec2f a, Vec2f b)
	{
		return new Vec2f((a.x + b.x), (a.y + b.y));
	}
	public Vec2f add(Vec2f a)
	{
		return add(this, a);
	}


	public static Vec2f subtract(Vec2f a, Vec2f b)
	{
		return new Vec2f((a.x - b.x), (a.y - b.y));
	}
	public Vec2f subtract(Vec2f a)
	{
		return subtract(this, a);
	}


	public static Vec2f scale(Vec2f a, float s)
	{
		return new Vec2f((a.x * s), (a.y * s));
	}
	public Vec2f scale(float s)
	{
		return scale(this, s);
	}


	public float lengthSquared()
	{
		return x * x + y * y;
	}
	public float length()
	{
		return (float)Math.sqrt(lengthSquared());
	}


	public static float distanceSquared(Vec2f a, Vec2f b)
	{
		return ((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
	public static float distance(Vec2f a, Vec2f b)
	{
		return (float)Math.sqrt(distanceSquared(a, b));
	}


	public static Vec2f normalized(Vec2f a)
	{
		return a.scale(1 / a.length());
	}
	public Vec2f normalized()
	{
		return normalized(this);
	}


	public boolean equals(Vec2f a) { return this.x == a.x && this.y == a.y; }


	public Vec2f clone() { return new Vec2f(x, y); }


	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
}
