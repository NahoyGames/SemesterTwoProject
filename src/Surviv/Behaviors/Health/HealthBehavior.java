package Surviv.Behaviors.Health;

import Util.Engine.GameBehavior;
import Util.Engine.Networking.NetGameEntity;

import java.awt.*;


public class HealthBehavior extends GameBehavior
{
	protected int health, maxHealth;
	protected NetGameEntity entity;


	public HealthBehavior(NetGameEntity entity, int maxHealth)
	{
		this.entity = entity;
		this.maxHealth = this.health = maxHealth;
	}


	public int getHealth()
	{
		return health;
	}


	public void drawHealthBar(Graphics2D renderBuffer, int height)
	{
		int width = 25;

		renderBuffer.setColor(new Color(68, 78, 92));
		renderBuffer.fillRect(-width/2, height, width, 5);

		renderBuffer.setColor(new Color(94, 136, 88));
		renderBuffer.fillRect(-width/2, height, (int)(width * ((float)health/maxHealth)), 5);
	}
}
