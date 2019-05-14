package Surviv.Behaviors.Weapons;

import Surviv.Entities.Server.Player;
import Util.Engine.GameEntity;


public class Ak47 extends AutoFireGun
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Weapons/ak47.png";


	public Ak47(GameEntity player)
	{
		super(player, SPRITE_PATH, 10, 1000, 7.5f);
	}
}
