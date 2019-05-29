package Surviv.Behaviors.Weapons;


import Util.Engine.GameEntity;
import Util.Math.NetInt32;

public class DesertEagle extends SpamClickGun
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Weapons/fists.png";


	public DesertEagle(GameEntity player, NetInt32 ammo)
	{
		super(player, ammo, SPRITE_PATH, 1000, 10);
	}
}
