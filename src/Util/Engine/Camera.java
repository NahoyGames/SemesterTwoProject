package Util.Engine;


import Util.Math.Vec2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Camera extends GameEntity
{
	private Color background;

	private float fov = 1f;

	private List<IDrawable> debugDrawables;
	private boolean drawDebug = false;


	public Camera(Scene scene, Color background)
	{
		super(scene, null);
		this.background = background;

		debugDrawables = new ArrayList<>();
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
		renderBuffer.scale(1 / fov, 1 / fov);
		renderBuffer.translate(-transform.position.x , transform.position.y);

		// Anti-aliasing
		renderBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderBuffer.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		renderBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		// Draws the entities in order
		for (IDrawable d : drawables.toArray(new IDrawable[drawables.size()]))
		{
			d.draw(renderBuffer);
		}

		// Draws debug graphics
		if (drawDebug)
		{
			for (IDrawable d : debugDrawables.toArray(new IDrawable[debugDrawables.size()]))
			{
				d.draw(renderBuffer);
			}
		}

		// Reset --> Camera Space
		renderBuffer.setTransform(new AffineTransform());

		// User Interface
		if (drawDebug)
		{
			renderBuffer.setColor(Color.GREEN);
			renderBuffer.drawString("DEBUG: ON", 10, 10);
		}
	}


	public Vec2f screenToWorldPoint(Vec2f screenPoint)
	{
		int width = Engine.canvas().getFrame().getWidth();
		int height = Engine.canvas().getFrame().getHeight();

		return new Vec2f((screenPoint.x - width / 2) + transform.position.x, -(screenPoint.y - height / 2) + transform.position.y);
	}


	public float getFov() { return fov; }
	public void setFov(float value) { fov = value; }


	public void addDebugDrawable(IDrawable drawable) { this.debugDrawables.add(drawable); }
	public void removeDebugDrawable(IDrawable drawable) { this.debugDrawables.remove(drawable); }


	@Override
	public void update()
	{
		super.update();

		if (Input.getButtonDown(KeyEvent.VK_CONTROL) && Input.getButtonDown(KeyEvent.VK_D))
		{
			drawDebug = !drawDebug;
		}
	}

	@Override
	public int getLayer()
	{
		return 0;
	}
}
