package Util.Engine.Networking;

import com.esotericsoftware.kryonet.Connection;

public interface INetworkListener
{
	void onReceivePacket(Connection sender, Packet packet);
}
