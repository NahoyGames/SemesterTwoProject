package Surviv.Entities.Items;

import Util.Engine.Scene;
import Util.Math.Vec2f;


public class Ak47Item extends LootItem
{
	private static final String SPRITE_PATH = "/Assets/Sprites/LootItems/ak47.png";


	public Ak47Item(Scene scene, Vec2f position)
	{
		super(scene, position, SPRITE_PATH);
	}


	@Override
	public String getName()
	{
		return "AK-47";
	}

	@Override
	public String getDescription()
	{
		return "A full auto AR";
	}
}
