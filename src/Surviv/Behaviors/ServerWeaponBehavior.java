package Surviv.Behaviors;


import Surviv.Entities.Server.Player;
import Util.Engine.GameEntity;
import Util.Engine.Time;
import Util.Math.NetInt32;


public abstract class ServerWeaponBehavior extends WeaponBehavior
{
	protected GameEntity player;

	protected float fireRate;
	protected float fireRateTimer;

	protected NetInt32 ammo;


	protected ServerWeaponBehavior(GameEntity player, NetInt32 ammo, String spritePath, int damage)
	{
		this(player, ammo, spritePath, 5, damage);
	}


	protected ServerWeaponBehavior(GameEntity player, NetInt32 ammo, String spritePath, float fireRate, int damage)
	{
		super(spritePath, damage);

		this.player = player;
		this.fireRate = fireRate;

		this.fireRateTimer = 0;

		this.ammo = ammo;
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
