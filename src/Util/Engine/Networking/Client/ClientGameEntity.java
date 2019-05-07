package Util.Engine.Networking.Client;

import Util.Engine.Engine;
import Util.Engine.Networking.NetGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Scene;


public abstract class ClientGameEntity extends NetGameEntity
{
	public ClientGameEntity(Scene scene, String spritePath, short networkId)
	{
		super(scene, spritePath, networkId);

		if (!(Engine.netManager() instanceof ClientNetManager))
		{
			System.err.println("Critical error: Attempting to spawn a CLIENT entity on a SERVER build!");
			System.exit(-1);
		}
	}


	protected final void sendReliable(Packet packet)
	{
		((ClientNetManager)Engine.netManager()).sendReliable(packet);
	}


	protected final void sendUnreliable(Packet packet)
	{
		((ClientNetManager)Engine.netManager()).sendUnreliable(packet);
	}
}
