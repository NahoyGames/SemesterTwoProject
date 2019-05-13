package Surviv.Entities.Environment;

import Util.Engine.GameEntity;
import Util.Engine.Scene;

public class Crate extends GameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Environment/crate.png";


	public Crate(Scene scene)
	{
		super(scene, SPRITE_PATH);
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
