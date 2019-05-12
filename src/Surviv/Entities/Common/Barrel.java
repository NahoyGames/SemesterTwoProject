package Surviv.Entities.Common;

import Util.Engine.GameEntity;
import Util.Engine.Scene;


public class Barrel extends GameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Environment/barrel.png";


	public Barrel(Scene scene)
	{
		super(scene, SPRITE_PATH);
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
