package Util.Engine;


import Util.Math.Vec2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;


public abstract class GameEntity implements IDrawable, IEngineEventListener
{
	// The scene in which this entity was instantiated
	protected Scene scene;

	// Transform of this entity. Every entity must have one, even it's not being used.
	protected Transform2D transform;

	// The image of this sprite
	private Image sprite;


	// Instantiates the entity in the given scene
	protected GameEntity(Scene scene, String spritePath)
	{
		this.scene = scene;
		this.transform = new Transform2D();

		try { this.sprite = ImageIO.read(new File(spritePath)); }
		catch (IOException e) { System.out.println("The sprite image was not found! Error: " + e); }
		catch (NullPointerException e) { /* Do nothing, simply means the entity doesn't use graphics */ }
	}


	// region This is now deprecated because it was wayyy to slow
	// Draws the sprite onto the screen, from the camera's perspective with a little bit of linear algebra :)
	/*@Override
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
	}*/
	// endregion


	@Override
	public final void draw(Graphics2D renderBuffer)
	{
		if (sprite == null) { return; }

		// Stores the --> Camera matrix
		AffineTransform oldMatrix = renderBuffer.getTransform();

		// Transforms Sprite file --> World space
		renderBuffer.scale(transform.scale.x, transform.scale.y);
		renderBuffer.translate(transform.position.x, -transform.position.y); // Negative y b/c top-left origin with java.swing :(
		renderBuffer.rotate(Math.toRadians(transform.rotation));

		// Draws the image, with the origin at the middle
		renderBuffer.drawImage(sprite, -sprite.getWidth(null) / 2, -sprite.getHeight(null) / 2, null);

		// Resets the transformations so that the next IDrawable can draw
		renderBuffer.setTransform(oldMatrix);
	}

	@Override
	public void onSceneLoad() { }


	@Override
	public void start() { }


	@Override
	public void update() { }


	@Override
	public void onDisable() { }


	@Override
	public void onSceneUnload() { }


	@Override
	public void onApplicationQuit() { }
}
