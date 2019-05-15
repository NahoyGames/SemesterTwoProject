package Surviv.Behaviors;


import Surviv.Entities.Server.Player;
import Util.Engine.GameEntity;
import Util.Engine.Time;


public abstract class ServerWeaponBehavior extends WeaponBehavior
{
	protected GameEntity player;

	protected float fireRate;
	protected float fireRateTimer;


	protected ServerWeaponBehavior(GameEntity player, String spritePath, int damage)
	{
		this(player, spritePath, 5, damage);
	}


	protected ServerWeaponBehavior(GameEntity player, String spritePath, float fireRate, int damage)
	{
		super(spritePath, damage);

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
		if (player instanceof Player)
		{
			super.update();
		}

		fireRateTimer += Time.deltaTime(true);
	}
}
