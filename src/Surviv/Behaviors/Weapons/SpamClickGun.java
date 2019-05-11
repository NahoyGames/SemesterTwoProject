package Surviv.Behaviors.Weapons;

import Surviv.Behaviors.WeaponBehavior;
import Surviv.Entities.Server.Bullet;
import Surviv.Entities.Server.Player;

import java.awt.event.KeyEvent;


public abstract class SpamClickGun extends WeaponBehavior
{
	private boolean canShoot; // True when the user released the key

	private float dist;


	public SpamClickGun(Player player, float dist)
	{
		super(player);

		this.dist = dist;
	}


	@Override
	public boolean tryUse()
	{
		if (canShoot)
		{
			canShoot = false;
			use();

			return true;
		}

		return false;
	}


	@Override
	public void use()
	{
		player.getScene().addEntity(new Bullet(player.getScene(), player.transform().position, player.transform().rotation, dist));
	}

	@Override
	public void update()
	{
		super.update();

		if (!player.getInputReceiver().getButtonDown(KeyEvent.VK_SPACE))
		{
			canShoot = true;
		}
	}
}
