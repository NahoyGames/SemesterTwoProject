package Surviv.Behaviors.Weapons;

import Surviv.Entities.Server.Player;
import Util.Engine.GameEntity;

public class DesertEagle extends SpamClickGun
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Weapons/fists.png";


	public DesertEagle(GameEntity player)
	{
		super(player, SPRITE_PATH, 1000);
	}
}
