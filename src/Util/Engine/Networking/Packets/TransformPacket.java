package Util.Engine.Networking.Packets;

import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Server.ServerGameEntity;
import Util.Math.Compressor;


public class TransformPacket extends Packet
{
	public short networkId;
	public float x;
	public float y;
	public byte rotation;


	public TransformPacket() { }


	public TransformPacket(ServerGameEntity target)
	{
		this.networkId = target.getNetworkId();
		this.x = target.transform().position.x;
		this.y = target.transform().position.y;
		this.rotation = Compressor.scaleFloatToByte(target.transform().rotation % 360, 0, 360);
	}
}
