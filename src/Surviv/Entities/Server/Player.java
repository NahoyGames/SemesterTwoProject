package Surviv.Entities.Server;

import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Server.ServerGameEntity;
import Util.Engine.Scene;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

public class Player extends ServerGameEntity
{
	private static final String SPRITE_PATH = "src/Assets/Sprites/Eye Base Color.png";


	public Player(Scene scene)
	{
		super(scene, SPRITE_PATH);
		transform.scale = new Vec2f(0.1f, 0.1f);
	}


	@Override
	public int getLayer()
	{
		return 0;
	}


	@Override
	public Class<? extends ClientGameEntity> getClientCounterpart()
	{
		return Surviv.Entities.Client.Player.class;
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet) { }
}
