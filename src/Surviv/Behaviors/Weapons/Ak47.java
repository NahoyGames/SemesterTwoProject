package Surviv.Behaviors.Weapons;

import Util.Engine.GameEntity;
import Util.Math.NetInt32;


public class Ak47 extends AutoFireGun
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Weapons/ak47.png";


	public Ak47(GameEntity player, NetInt32 ammo)
	{
		super(player, ammo, SPRITE_PATH, 10, 1000, 7.5f, 5);
	}
}
