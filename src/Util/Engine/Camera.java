package Util.Engine;


import Util.Math.Vec2f;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Camera extends GameEntity
{
	private Color background;


	public Camera(Scene scene, Color background)
	{
		super(scene, null);
		this.background = background;
	}


	public void renderFrame(List<IDrawable> drawables, Canvas destCanvas)
	{
		// Retrieves the render buffer
		Graphics2D renderBuffer = destCanvas.getRenderBuffer();
		int width = destCanvas.getFrame().getWidth(), height = destCanvas.getFrame().getHeight();

		// Background
		renderBuffer.setColor(background);
		renderBuffer.fillRect(0, 0, width, height);

		// Applies the World --> Camera space transformation
		renderBuffer.translate(width / 2, height / 2);
		renderBuffer.rotate(-transform.rotation);
		renderBuffer.translate(-transform.position.x , transform.position.y);

		// Draws the entities in order
		for (IDrawable d : drawables.toArray(new IDrawable[drawables.size()]))
		{
			d.draw(renderBuffer);
		}
	}


	public Vec2f screenToWorldPoint(Vec2f screenPoint)
	{
		int width = Engine.canvas().getFrame().getWidth();
		int height = Engine.canvas().getFrame().getHeight();

		return new Vec2f((screenPoint.x - width / 2) + transform.position.x, -(screenPoint.y - height / 2) + transform.position.y);
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
