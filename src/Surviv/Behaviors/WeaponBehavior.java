package Surviv.Behaviors;

import Surviv.Entities.Server.Bullet;
import Surviv.Entities.Server.Player;
import Util.Engine.GameBehavior;
import Util.Engine.Time;


public abstract class WeaponBehavior extends GameBehavior
{
	protected Player player;

	protected float fireRate;
	private float fireRateTimer;


	protected WeaponBehavior(Player player)
	{
		this(player, 5);
	}


	protected WeaponBehavior(Player player, float fireRate)
	{
		this.player = player;
		this.fireRate = fireRate;

		this.fireRateTimer = 0;
	}


	public abstract void use();


	public boolean tryUse()
	{
		if (fireRateTimer >=  1f / fireRate)
		{
			fireRateTimer = 0;
			use();

			return true;
		}

		return false;
	}

	@Override
	public void update()
	{
		super.update();

		fireRateTimer += Time.deltaTime(true);
	}
}
