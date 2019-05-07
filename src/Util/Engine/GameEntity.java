package Util.Engine;


import Util.Math.Vec2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;


public abstract class GameEntity implements IDrawable, IEngineEventListener
{
	// The scene in which this entity was instantiated
	protected Scene scene;

	// Transform of this entity. Every entity must have one, even it's not being used.
	protected Transform2D transform;

	// Behaviors on this entity
	protected HashSet<GameBehavior> behaviors;

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

		this.behaviors = new HashSet<>();
	}


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


	public Transform2D transform() { return transform; }


	public void addBehavior(GameBehavior behavior)
	{
		this.behaviors.add(behavior);
	}


	@Override
	public void onSceneLoad()
	{
		for (GameBehavior b : behaviors)
		{
			b.onSceneLoad();
		}
	}


	@Override
	public void start()
	{
		for (GameBehavior b : behaviors)
		{
			b.start();
		}
	}


	@Override
	public void update()
	{
		for (GameBehavior b : behaviors)
		{
			b.update();
		}
	}


	@Override
	public void onDisable()
	{
		for (GameBehavior b : behaviors)
		{
			b.onDisable();
		}
	}


	@Override
	public void onSceneUnload()
	{
		for (GameBehavior b : behaviors)
		{
			b.onSceneUnload();
		}
	}


	@Override
	public void onApplicationQuit()
	{
		for (GameBehavior b : behaviors)
		{
			b.onApplicationQuit();
		}
	}
}
