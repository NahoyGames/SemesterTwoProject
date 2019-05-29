package Surviv.Behaviors.Weapons;

import Surviv.Entities.Server.Bullet;
import Util.Engine.GameEntity;
import Util.Math.NetInt32;

public class Shotgun extends ClickCooldownGun
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Weapons/shotgun.png";

	private final float spread = 25f;
	private final int shells = 15;


	public Shotgun(GameEntity player, NetInt32 ammo)
	{
		super(player, ammo, SPRITE_PATH, 0.67f, 300, 3);
	}


	@Override
	public void use()
	{
		for (int i = 0; i < shells; i++)
		{
			if (ammo.value > 0)
			{
				ammo.value -= 1;

				float randomSpread = ((float) Math.random() - 0.5f) * spread;
				player.getScene().addEntity(new Bullet(player.getScene(), player.transform().position.add(player.transform().forward().scale(30)), player.transform().rotation + randomSpread, dist, damage));
			}
		}
	}
}
