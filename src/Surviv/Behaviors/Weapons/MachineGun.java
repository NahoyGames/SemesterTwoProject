package Surviv.Behaviors.Weapons;

import Surviv.Entities.Server.Player;
import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Engine.Time;
import Util.Math.NetInt32;

public class MachineGun extends AutoFireGun
{
	private static final String SPRITE_PATH = "/Assets/Sprites/Weapons/fists.png";

	private float loadUpTimer;


	public MachineGun(GameEntity player, NetInt32 ammo)
	{
		super(player, ammo, SPRITE_PATH, 20, 700, 25, 3);
	}


	@Override
	public void update()
	{
		super.update();

		if (player instanceof Player && !((Player)player).getInputReceiver().getButtonDown(Engine.config().MOUSE_KEYCODE))
		{
			loadUpTimer = Math.max(0, loadUpTimer - Time.deltaTime(true) * 2);
		}
		else
		{
			loadUpTimer += Time.deltaTime(true);

			fireRate = Math.min(loadUpTimer * 10, 20);
		}
	}
}
