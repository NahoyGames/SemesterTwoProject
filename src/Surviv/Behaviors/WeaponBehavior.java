package Surviv.Behaviors;

import Util.Engine.GameBehavior;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class WeaponBehavior extends GameBehavior
{
	private Image sprite;
	protected int damage;


	public WeaponBehavior(String spritePath, int damage)
	{
		try { this.sprite = ImageIO.read(getClass().getResource(spritePath)); }
		catch (IOException e) { System.out.println("The sprite image was not found! Error: " + e.toString()); e.printStackTrace(); }
		catch (NullPointerException e) { /* Do nothing, simply means the entity doesn't use graphics */ }

		this.damage = damage;
	}


	public Image getGraphics()
	{
		return sprite;
	}
}
