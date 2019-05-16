package Surviv.Behaviors.Health;

import Surviv.Networking.Packets.HealthPacket;
import Util.Engine.Engine;
import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.NetGameEntity;
import Util.Engine.Networking.Packet;
import Util.Math.Compressor;
import com.esotericsoftware.kryonet.Connection;


public class ClientHealthBehavior extends HealthBehavior implements INetworkListener
{
	public ClientHealthBehavior(NetGameEntity entity, int maxHealth)
	{
		super(entity, maxHealth);

		Engine.netManager().addListener(this);
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (packet instanceof HealthPacket && ((HealthPacket) packet).entityNetworkId == entity.getNetworkId())
		{
			this.health = (int)Compressor.scaleByteToFloat(((HealthPacket) packet).newHealth, 0, maxHealth);

			if (this.health <= 0)
			{
				die();
			}
		}
	}


	@Override
	public void onDisable()
	{
		super.onDisable();

		Engine.netManager().removeListener(this);
	}
}
