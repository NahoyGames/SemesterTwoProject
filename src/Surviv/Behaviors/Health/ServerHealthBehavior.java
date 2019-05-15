package Surviv.Behaviors.Health;

import Surviv.Networking.Packets.HealthPacket;
import Util.Engine.Engine;
import Util.Engine.Networking.NetGameEntity;
import Util.Engine.Networking.Server.ServerNetManager;

public class ServerHealthBehavior extends HealthBehavior
{
	public ServerHealthBehavior(NetGameEntity entity, int maxHealth)
	{
		super(entity, maxHealth);
	}


	public void setHealth(int newHealth)
	{
		this.health = Math.max(Math.min(newHealth, maxHealth), 0);

		((ServerNetManager)Engine.netManager()).sendReliable(new HealthPacket(health, maxHealth, entity.getNetworkId()));
	}


	public void damage(int amount)
	{
		setHealth(getHealth() - amount);
	}
}
