package Util.Engine;


import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GameEntity implements IDrawable, IEngineEventListener
{
	protected Transform2D transform;
	protected Scene scene;

	private List<EngineEventListener> behaviors;

	private BufferedImage sprite;


	// Instantiates the entity in the given scene
	protected GameEntity(Scene scene, String spritePath, EngineEventListener... behaviors)
	{
		this.scene = scene;
		this.transform = new Transform2D();
		this.behaviors = new ArrayList<>(Arrays.asList(behaviors));

		try { this.sprite = ImageIO.read(new File(spritePath)); }
		catch (IOException e) { System.out.println("The sprite image was not found! Error: " + e); }
		catch (NullPointerException e) { /* Do nothing, simply means the entity doesn't use graphics */ }
	}


	public void addBehavior(EngineEventListener behavior)
	{
		this.behaviors.add(behavior);
		behavior.awake();
		behavior.start();
	}


	public void removeBehavior(EngineEventListener behavior)
	{
		this.behaviors.remove(behavior);
		behavior.onDisable();
	}


	public Transform2D transform() { return transform; }


	// Draws the sprite onto the screen, from the camera's perspective with a little bit of linear algebra :)
	@Override
	public final void draw(BufferedImage renderBuffer, AffineTransform cameraMatrix)
	{
		if (this.sprite == null)
		{
			return;
		}

		// Image space --> World space
		AffineTransform transformMatrix = new AffineTransform()
		{
			{
				scale(transform.scale.x, transform.scale.y);
				rotate(Math.toRadians(transform.rotation));
				translate(transform.position.x, transform.position.y);
			}
		};
		// Image space --> Screen space
		AffineTransform imageToScreenMatrix = new AffineTransform()
		{
			{
				concatenate(cameraMatrix);
				concatenate(transformMatrix);
			}
		};

		for (int x = 0; x < sprite.getWidth(); x++)
		{
			for (int y = 0; y < sprite.getHeight(); y++)
			{
				Point2D.Float screenPoint = new Point2D.Float();
				imageToScreenMatrix.transform(new Point2D.Float((float)x - sprite.getWidth() / 2, (float)y - sprite.getHeight() / 2), screenPoint);

				// Is actually on screen?
				if (screenPoint.x >= 0 && screenPoint.x < renderBuffer.getWidth()
					&& screenPoint.y >= 0 && screenPoint.y < renderBuffer.getHeight())
				{
					renderBuffer.setRGB((int)screenPoint.x, (int)screenPoint.y, sprite.getRGB(x, y));
				}
			}
		}
	}


	@Override
	public void onSceneLoad()
	{
		for (EngineEventListener b : behaviors)
		{
			b.onSceneLoad();
		}
	}


	@Override
	public void awake()
	{
		for (EngineEventListener b : behaviors)
		{
			b.awake();
		}
	}


	@Override
	public void start()
	{
		for (EngineEventListener b : behaviors)
		{
			b.start();
		}
	}


	@Override
	public void update()
	{
		for (EngineEventListener b : behaviors)
		{
			b.update();
		}
	}


	@Override
	public void onDisable()
	{
		for (EngineEventListener b : behaviors)
		{
			b.update();
		}
	}


	@Override
	public void onSceneUnload()
	{
		for (EngineEventListener b : behaviors)
		{
			b.onSceneUnload();
		}
	}


	@Override
	public void onApplicationQuit()
	{
		for (EngineEventListener b : behaviors)
		{
			b.onApplicationQuit();
		}
	}
}
