package Util.Engine.Physics;

import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Engine.IDrawable;
import Util.Math.Vec2f;

import java.awt.*;
import java.awt.geom.AffineTransform;


public abstract class Collider implements IDrawable
{
	public final boolean isStatic;

	private GameEntity entity;


	protected Collider(boolean isStatic, GameEntity entity)
	{
		this.isStatic = isStatic;
		this.entity = entity;

		entity.getScene().camera().addDebugDrawable(this);
	}


	public Vec2f getOrigin()
	{
		return getEntity().transform().position.add(getEntity().anchor().subtract(new Vec2f(0.5f, 0.5f)).scale(getEntity().transform().scale));
	}


	public GameEntity getEntity()
	{
		return entity;
	}


	public void onCollision(Collider other, CollisionInfo info)
	{

	}


	protected abstract void drawDebug(Graphics2D renderBuffer);


	@Override
	public void draw(Graphics2D renderBuffer)
	{
		// Stores the --> Camera matrix
		AffineTransform oldMatrix = renderBuffer.getTransform();

		// --> World Space
		Vec2f origin = getOrigin();
		renderBuffer.translate(origin.x, -origin.y);

		// Draws the collider
		renderBuffer.setColor(isStatic ? Color.RED : Color.GREEN);
		renderBuffer.fillOval(-3, -3, 6, 6);
		drawDebug(renderBuffer);

		// Resets the transformations so that the next IDrawable can draw
		renderBuffer.setTransform(oldMatrix);
	}


	@Override
	public int getLayer()
	{
		return 999;
	}


	@Override
	public float getAlpha()
	{
		return 1;
	}


	@Override
	public float setAlpha(float val)
	{
		return 1;
	}
}
