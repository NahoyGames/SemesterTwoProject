package Surviv.Behaviors.Weapons;

import Surviv.Entities.Server.Bullet;
import Surviv.Entities.Server.Player;

public class Shotgun extends ClickCooldownGun
{
	private final float spread = 25f;
	private final int shells = 15;


	public Shotgun(Player player)
	{
		super(player, 0.67f, 300);
	}


	@Override
	public void use()
	{
		for (int i = 0; i < shells; i++)
		{
			float randomSpread = ((float) Math.random() - 0.5f) * spread;
			player.getScene().addEntity(new Bullet(player.getScene(), player.transform().position, player.transform().rotation + randomSpread, 300));
		}
	}
}
