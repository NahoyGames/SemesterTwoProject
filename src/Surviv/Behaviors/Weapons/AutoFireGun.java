package Surviv.Behaviors.Weapons;

import Surviv.Behaviors.WeaponBehavior;
import Surviv.Entities.Server.Bullet;
import Surviv.Entities.Server.Player;

public abstract class AutoFireGun extends WeaponBehavior
{
	protected float dist;
	protected float spread;


	public AutoFireGun(Player player, float fireRate, float dist, float spread)
	{
		super(player, fireRate);

		this.dist = dist;
		this.spread = spread;
	}


	@Override
	public void use()
	{
		float randomSpread = ((float)Math.random() - 0.5f) * spread;
		player.getScene().addEntity(new Bullet(player.getScene(), player.transform().position.add(player.transform().forward().scale(30)), player.transform().rotation + randomSpread, dist));
	}
}
