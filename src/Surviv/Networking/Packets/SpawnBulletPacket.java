package Surviv.Networking.Packets;

import Util.Engine.Networking.Packet;
import Util.Math.Compressor;
import Util.Math.Vec2f;

public class SpawnBulletPacket extends Packet
{
	public Vec2f origin;
	public byte dir;


	public SpawnBulletPacket() { }


	public SpawnBulletPacket(Vec2f origin, float dir)
	{
		this.origin = origin;
		this.dir = Compressor.scaleFloatToByte(dir % 360, 0, 360);
	}
}
