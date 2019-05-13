package Surviv.Behaviors.Weapons;

import Surviv.Behaviors.WeaponBehavior;
import Surviv.Entities.Server.Bullet;
import Surviv.Entities.Server.Player;
import Util.Engine.Engine;

public class ClickCooldownGun extends WeaponBehavior
{
	private boolean canShoot; // True when the user released the key

	private float dist;


	public ClickCooldownGun(Player player, float fireRate, float dist)
	{
		super(player, fireRate);

		this.dist = dist;
	}


	@Override
	public boolean tryUse()
	{
		if (fireRateTimer >=  1f / fireRate && canShoot)
		{
			fireRateTimer = 0;
			canShoot = false;
			use();

			return true;
		}

		return false;
	}


	@Override
	public void use()
	{
		player.getScene().addEntity(new Bullet(player.getScene(), player.transform().position.add(player.transform().forward().scale(30)), player.transform().rotation, dist));
	}


	@Override
	public void update()
	{
		super.update();

		if (!player.getInputReceiver().getButtonDown(Engine.config().MOUSE_KEYCODE))
		{
			canShoot = true;
		}
	}
}
