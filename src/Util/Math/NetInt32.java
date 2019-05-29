package Util.Math;


import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.NetInt32Packet;
import Util.Engine.Networking.Server.ServerNetManager;
import Util.Engine.Scene;
import com.esotericsoftware.kryonet.Connection;


// A quick way to sync a single integer value over the network
public class NetInt32 extends GameEntity implements INetworkListener
{
	public int value;
	private int lastSent;

	private Class<? extends NetInt32Packet> updatePacketType;


	public NetInt32(int value, Scene scene, Class<? extends NetInt32Packet> updatePacketType)
	{
		super(scene,null);

		this.value = value;
		this.updatePacketType = updatePacketType;

		scene.addEntity(this);
		Engine.netManager().addListener(this);
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (updatePacketType.isInstance(packet))
		{
			System.out.println("Received int32 packet");

			this.value = ((NetInt32Packet)packet).value;
		}
	}


	@Override
	public int getLayer()
	{
		return 0;
	}


	@Override
	public void update()
	{
		if (Engine.config().IS_SERVER_BUILD && value != lastSent)
		{
			System.out.println("Sending int32 packet");

			try
			{
				((ServerNetManager)Engine.netManager()).sendReliable(updatePacketType.getConstructor(int.class).newInstance((lastSent = value)));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
