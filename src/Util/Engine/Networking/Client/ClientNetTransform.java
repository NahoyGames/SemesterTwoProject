package Util.Engine.Networking.Client;

import Util.Engine.Engine;
import Util.Engine.GameBehavior;
import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.TransformPacket;
import Util.Math.Compressor;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;


public class ClientNetTransform extends GameBehavior implements INetworkListener
{
	private ClientGameEntity target;


	public ClientNetTransform(ClientGameEntity target)
	{
		this.target = target;

		Engine.netManager().addListener(this);
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (packet instanceof TransformPacket)
		{
			TransformPacket transformPacket = (TransformPacket)packet;

			if (transformPacket.networkId == target.getNetworkId())
			{
				target.transform().position = new Vec2f(transformPacket.x, transformPacket.y);
				target.transform().rotation = Compressor.scaleByteToFloat(transformPacket.rotation, 0, 360);
			}
		}
	}
}
