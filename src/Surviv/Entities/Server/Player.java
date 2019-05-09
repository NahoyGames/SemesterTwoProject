package Surviv.Entities.Server;


import Surviv.Networking.Packets.ClientLookAtPacket;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.LinkClientToEntityPacket;
import Util.Engine.Networking.Server.ServerGameEntity;
import Util.Engine.Networking.Server.ServerInputReceiver;
import Util.Engine.Networking.Server.ServerNetTransform;
import Util.Engine.Scene;
import Util.Math.Compressor;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.event.KeyEvent;


public class Player extends ServerGameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/character.png";

	private Connection connection;


	private ServerInputReceiver inputReceiver;


	public Player(Scene scene, Connection connection)
	{
		super(scene, SPRITE_PATH);

		// Initial transforms
		transform.scale = new Vec2f(0.1f, 0.1f);

		// Assign client
		this.connection = connection;
		connection.sendTCP(new LinkClientToEntityPacket(getNetworkId()));

		// Initial behaviors
		addBehavior(new ServerNetTransform(this, 9));
		this.inputReceiver = (ServerInputReceiver) addBehavior(new ServerInputReceiver(connection));
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
	public void update()
	{
		super.update();

		if (inputReceiver.getButtonDown(KeyEvent.VK_A))
		{
			transform.position.x -= 5;

			System.out.println("a was pressed!");
		}

		if (inputReceiver.getButtonDown(KeyEvent.VK_D))
		{
			transform.position.x += 5;
		}

		if (inputReceiver.getButtonDown(KeyEvent.VK_W))
		{
			transform.position.y += 5;
		}

		if (inputReceiver.getButtonDown(KeyEvent.VK_S))
		{
			transform.position.y -= 5;
		}
	}

	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (packet instanceof ClientLookAtPacket)
		{
			ClientLookAtPacket lookAtPacket = (ClientLookAtPacket)packet;

			System.out.println("received look at " + Compressor.scaleByteToFloat(lookAtPacket.rotation, -180, 180));
			transform.rotation = Compressor.scaleByteToFloat(lookAtPacket.rotation, -180, 180);
		}
	}
}
