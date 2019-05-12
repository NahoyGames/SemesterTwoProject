package Util.Engine.Physics.Colliders;

import Util.Engine.GameEntity;
import Util.Engine.Physics.Collider;
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
	protected void drawDebug(Graphics2D renderBuffer)
	{
		renderBuffer.drawRect((int)-halfSize.x, (int)-halfSize.y, (int)halfSize.x, (int)halfSize.y);
	}
}
