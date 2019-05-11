package Util.Engine;


import Util.Math.Vec2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
	protected Vec2f anchor;


	// Instantiates the entity in the given scene
	protected GameEntity(Scene scene, String spritePath, Vec2f anchor)
	{
		this.scene = scene;
		this.transform = new Transform2D();

		try { this.sprite = ImageIO.read(getClass().getResource(spritePath)); }
		catch (IOException e) { System.out.println("The sprite image was not found! Error: " + e.toString()); e.printStackTrace(); }
		catch (NullPointerException e) { /* Do nothing, simply means the entity doesn't use graphics */ }

		this.anchor = anchor;

		this.behaviors = new HashSet<>();
	}


	protected GameEntity(Scene scene, String spritePath)
	{
		this(scene, spritePath, new Vec2f(0.5f, 0.5f));
	}


	@Override
	public final void draw(Graphics2D renderBuffer)
	{
		if (sprite == null) { return; }

		// Stores the --> Camera matrix
		AffineTransform oldMatrix = renderBuffer.getTransform();

		// Transforms Sprite file --> World space
		renderBuffer.translate(transform.position.x, -transform.position.y); // Negative y b/c top-left origin with java.swing :(
		renderBuffer.rotate(Math.toRadians(transform.rotation));
		renderBuffer.scale(transform.scale.x, transform.scale.y);
		renderBuffer.translate(-(sprite.getWidth(null) * anchor.x), -(sprite.getHeight(null) * anchor.y));

		// Draws the image, with the origin at the middle
		renderBuffer.drawImage(sprite, 0, 0, null);

		// Resets the transformations so that the next IDrawable can draw
		renderBuffer.setTransform(oldMatrix);
	}


	public Transform2D transform() { return transform; }


	public GameBehavior addBehavior(GameBehavior behavior)
	{
		if (this.behaviors.add(behavior))
		{
			return behavior;
		}

		return null;
	}


	public Scene getScene() { return scene; }


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
