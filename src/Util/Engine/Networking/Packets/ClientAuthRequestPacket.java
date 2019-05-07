package Util.Engine.Networking.Packets;

import Util.Engine.Engine;
import Util.Engine.Networking.Packet;


public class ClientAuthRequestPacket extends Packet
{
	public String version;
	public String username;


	public ClientAuthRequestPacket() { }


	public ClientAuthRequestPacket(String username)
	{
		this.version = Engine.config().VERSION;
		this.username = username;
	}
}
