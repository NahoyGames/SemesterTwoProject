package Surviv.Entities.Server;

import Surviv.Entities.Environment.IEnvironment;
import Util.Engine.GameEntity;
import Util.Engine.Scene;

public class LootCrate extends GameEntity implements IEnvironment
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Environment/crate.png";


	public LootCrate(Scene scene)
	{
		super(scene, SPRITE_PATH);
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
