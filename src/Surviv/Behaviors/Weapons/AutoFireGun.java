package Surviv.Behaviors.Weapons;

import Surviv.Behaviors.ServerWeaponBehavior;
import Surviv.Entities.Server.Bullet;
import Util.Engine.GameEntity;
import Util.Math.NetInt32;

public abstract class AutoFireGun extends ServerWeaponBehavior
{
	protected float dist;
	protected float spread;


	public AutoFireGun(GameEntity player, NetInt32 ammo, String spritePath, float fireRate, float dist, float spread, int damage)
	{
		super(player, ammo, spritePath, fireRate, damage);

		this.dist = dist;
		this.spread = spread;
	}


	@Override
	public void use()
	{
		if (ammo.value > 0)
		{
			float randomSpread = ((float) Math.random() - 0.5f) * spread;
			ammo.value -= 1;
			player.getScene().addEntity(new Bullet(player.getScene(), player.transform().position.add(player.transform().forward().scale(30)), player.transform().rotation + randomSpread, dist, damage));
		}
	}
}
