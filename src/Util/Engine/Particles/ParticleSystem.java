package Util.Engine.Particles;

import Util.Engine.*;
import Util.Math.Vec2f;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public class ParticleSystem extends GameEntity
{
	private class Particle
	{
		private Vec2f pos;
		private Vec2f dir;
		private float lifetime;


		public Particle(Vec2f dir)
		{
			this.dir = dir.scale(speed);
			this.pos = transform().position;
			this.lifetime = 0f;
		}


		private void update()
		{
			this.lifetime += Time.deltaTime(true);

			pos = pos.add(dir);

			if (lifetime >= particleLifetime)
			{
				particles.remove(this);
			}
		}


		private void draw(Graphics2D renderBuffer)
		{
			// Stores the --> Camera matrix
			AffineTransform oldMatrix = renderBuffer.getTransform();

			// Transforms Sprite file --> World space
			renderBuffer.translate(pos.x, -pos.y); // Negative y b/c top-left origin with java.swing :(
			//renderBuffer.rotate(Math.toRadians(transform.rotation));
			//renderBuffer.scale(transform.scale.x, transform.scale.y);
			renderBuffer.translate(-(particleImage.getWidth(null) * 0.5f), -(particleImage.getHeight(null) * 0.5f));

			// Alpha
			renderBuffer.setComposite(AlphaComposite.SrcOver.derive(getAlpha()));

			// Draw the graphics
			renderBuffer.drawImage(particleImage, 0, 0, null);

			// Resets the transformations so that the next IDrawable can draw
			renderBuffer.setTransform(oldMatrix);
		}
	}


	// Parameters
	private float particlesPerSecond, particleLifetime, systemDuration;
	private Image particleImage;
	private float angleMin, angleMax, speed;

	private LinkedList<Particle> particles; // linked list because particles get constantly removed and added
	private float timer, totalTimer;


	public ParticleSystem(Scene scene, ParticleSystemConfig config, Vec2f spawnPoint, Vec2f scale)
	{
		super(scene, null);

		this.particlesPerSecond = config.particlesPerSecond;
		this.particleLifetime = config.particleLifetime;
		this.systemDuration = config.systemDuration;
		this.particleImage = config.particleImage;
		this.angleMin = config.angleMin;
		this.angleMax = config.angleMax;
		this.speed = config.speed;

		particles = new LinkedList<>();

		transform.scale = scale.clone();
		transform.position = spawnPoint.clone();
	}


	@Override
	protected void drawSprite(Graphics2D renderBuffer)
	{
		super.drawSprite(renderBuffer);

		for (Particle p : particles)
		{
			p.draw(renderBuffer);
		}
	}


	@Override
	public void update()
	{
		super.update();

		// Update particles
		for (Particle p : particles.toArray(new Particle[particles.size()]))
		{
			p.update();
		}

		// Spawn new particles
		timer += Time.deltaTime(true);
		if (timer >= 1 / particlesPerSecond)
		{
			timer = 0;
			spawnParticle();
		}

		// Lifetime Checker
		totalTimer += Time.deltaTime(true);
		if (totalTimer >= systemDuration)
		{
			scene.removeEntity(this);
		}
	}


	@Override
	public int getLayer()
	{
		return 0;
	}


	private void spawnParticle()
	{
		float angle = (float)Math.toRadians(angleMin + Math.random() * (angleMax - angleMin));

		particles.add(new Particle(new Vec2f((float)Math.cos(angle), (float)Math.sin(angle))));
	}
}
