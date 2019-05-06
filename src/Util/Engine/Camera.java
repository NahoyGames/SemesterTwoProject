package Util.Engine;


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


	@Override
	public void update()
	{
		float x = Engine.input().getAxis("Horizontal");
		float y = Engine.input().getAxis("Vertical");

		//transform.position.x += x;
		//transform.position.y += y;
	}

	@Override
	public int getLayer()
	{
		return 0;
	}
}
