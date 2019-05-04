package Util.Engine;

import Surviv.Behaviors.TestInputMove;
import Surviv.Behaviors.TestRandomMove;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class Camera extends GameEntity
{
	public Camera(Scene scene)
	{
		super(scene, null);
		addBehavior(new TestInputMove(this));
	}


	public void renderFrame(List<IDrawable> entities, BufferedImage renderBuffer)
	{
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
