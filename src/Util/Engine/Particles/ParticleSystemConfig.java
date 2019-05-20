package Util.Engine.Particles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ParticleSystemConfig
{
	public final float particlesPerSecond, particleLifetime, systemDuration;
	public Image particleImage;
	public final float angleMin, angleMax, speed;


	public ParticleSystemConfig(float particlesPerSecond, float particleLifetime, float systemDuration, String particleImagePath, float angleMin, float angleMax, float speed)
	{
		this.particlesPerSecond = particlesPerSecond;
		this.particleLifetime = particleLifetime;
		this.systemDuration = systemDuration;
		try { this.particleImage = ImageIO.read(getClass().getResource(particleImagePath)); }
		catch (IOException e) { System.out.println("The sprite image was not found! Error: " + e.toString()); e.printStackTrace(); }
		catch (NullPointerException e) { /* Do nothing, simply means the entity doesn't use graphics */ }
		this.angleMin = angleMin;
		this.angleMax = angleMax;
		this.speed = speed;
	}
}
