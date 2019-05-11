package Surviv.Behaviors.Weapons;

import Surviv.Entities.Server.Player;


public class Ak47 extends AutoFireGun
{
	public Ak47(Player player)
	{
		super(player, 10, 1000, 7.5f);
	}
}
