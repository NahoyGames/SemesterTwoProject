package Util.Engine;

import Surviv.Behaviors.TestInputMove;
import Surviv.Behaviors.TestRandomMove;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class Camera extends GameEntity
{
	private int background;


	public Camera(Scene scene, Color background)
	{
		super(scene, null);
		this.background = background.getRGB();

		addBehavior(new TestInputMove(this));
	}


	public void renderFrame(List<IDrawable> entities, BufferedImage renderBuffer)
	{
		// Background
		/*for (int x = 0; x < renderBuffer.getWidth(); x++)
		{
			for (int y = 0; y < renderBuffer.getHeight(); y++)
			{
				renderBuffer.setRGB(x, y, background);
			}
		}*/

		// GameEntities
		for (IDrawable e : entities)
		{
			e.draw(renderBuffer, getCameraMatrix(renderBuffer.getWidth(), renderBuffer.getHeight()));
		}
	}


	public AffineTransform getCameraMatrix(int width, int height)
	{
		return new AffineTransform()
		{
			{
				translate(width/2, height/2);
				rotate(Math.toRadians(-transform.rotation));
				translate(-transform.position.x, transform.position.y);
			}
		};
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
