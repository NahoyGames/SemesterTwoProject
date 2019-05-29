package Surviv.Behaviors.Weapons;

import Surviv.Behaviors.ServerWeaponBehavior;
import Surviv.Entities.Server.Bullet;
import Surviv.Entities.Server.Player;
import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Math.NetInt32;


public abstract class SpamClickGun extends ServerWeaponBehavior
{
	private boolean canShoot; // True when the user released the key

	private float dist;


	public SpamClickGun(GameEntity player, NetInt32 ammo, String spritePath, float dist, int damage)
	{
		super(player, ammo, spritePath, damage);

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
		if (ammo.value > 0)
		{
			ammo.value -= 1;
			player.getScene().addEntity(new Bullet(player.getScene(), player.transform().position.add(player.transform().forward().scale(30)), player.transform().rotation, dist, damage));
		}
	}

	@Override
	public void update()
	{
		super.update();

		if (player instanceof Player && !((Player)player).getInputReceiver().getButtonDown(Engine.config().MOUSE_KEYCODE))
		{
			canShoot = true;
		}
	}
}
