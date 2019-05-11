package Surviv.Networking.Packets;

import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
import Util.Engine.Networking.Packet;
import Util.Math.Compressor;
import Util.Math.Vec2f;

public class SpawnBulletPacket extends Packet
{
	public Vec2f origin;
	public byte dir;
	public byte dist;


	public SpawnBulletPacket() { }


	public SpawnBulletPacket(Vec2f origin, float dir, float dist)
	{
		this.origin = origin;
		this.dir = Compressor.scaleFloatToByte(dir % 360, 0, 360);
		this.dist = Compressor.scaleFloatToByte(dist, 0, ((SurvivEngineConfiguration)Engine.config()).MAX_BULLET_DISTANCE);
	}
}
