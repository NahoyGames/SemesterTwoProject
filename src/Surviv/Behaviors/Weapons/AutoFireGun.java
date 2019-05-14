package Surviv.Behaviors.Weapons;

import Surviv.Behaviors.ServerWeaponBehavior;
import Surviv.Entities.Server.Bullet;
import Surviv.Entities.Server.Player;
import Util.Engine.GameEntity;

public abstract class AutoFireGun extends ServerWeaponBehavior
{
	protected float dist;
	protected float spread;


	public AutoFireGun(GameEntity player, String spritePath, float fireRate, float dist, float spread)
	{
		super(player, spritePath, fireRate);

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
