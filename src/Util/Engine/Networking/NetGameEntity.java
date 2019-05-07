package Util.Engine.Networking;

import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Engine.Scene;

public abstract class NetGameEntity extends GameEntity implements INetworkListener
{
	private short networkId;


	public NetGameEntity(Scene scene, String spritePath, short networkId)
	{
		super(scene, spritePath);

		this.networkId = networkId;

		Engine.netManager().addListener(this);
	}


	public short getNetworkId() { return networkId; }
}
