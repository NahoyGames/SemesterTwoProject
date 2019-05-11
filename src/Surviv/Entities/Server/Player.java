package Surviv.Entities.Server;


import Surviv.Behaviors.WeaponBehavior;
import Surviv.Behaviors.Weapons.Ak47;
import Surviv.Behaviors.Weapons.DesertEagle;
import Surviv.Behaviors.Weapons.Shotgun;
import Surviv.Networking.Packets.ClientLookAtPacket;
import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.LinkClientToEntityPacket;
import Util.Engine.Networking.Server.ServerGameEntity;
import Util.Engine.Networking.Server.ServerInputReceiver;
import Util.Engine.Networking.Server.ServerNetTransform;
import Util.Engine.Scene;
import Util.Engine.Time;
import Util.Math.Compressor;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.event.KeyEvent;


public class Player extends ServerGameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/character.png";

	private Connection connection;


	private ServerInputReceiver inputReceiver;


	private int equippedWeaponIndex;
	private WeaponBehavior[] inventory;


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

		// Inventory
		inventory = new WeaponBehavior[]
				{
						(WeaponBehavior) addBehavior(new Ak47(this)),
						(WeaponBehavior) addBehavior(new Shotgun(this))
				};
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


	public ServerInputReceiver getInputReceiver() { return inputReceiver; }


	@Override
	public void update()
	{
		super.update();

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration) Engine.config()).MOVE_LEFT_KEY))
		{
			transform.position.x -= 150 * Time.deltaTime(true);

			System.out.println("a was pressed!");
		}

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).MOVE_RIGHT_KEY))
		{
			transform.position.x += 150 * Time.deltaTime(true);
		}

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).MOVE_UP_KEY))
		{
			transform.position.y += 150 * Time.deltaTime(true);
		}

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).MOVE_DOWN_KEY))
		{
			transform.position.y -= 150 * Time.deltaTime(true);
		}

		if (inputReceiver.getButtonDown(KeyEvent.VK_SPACE))
		{
			inventory[equippedWeaponIndex].tryUse();
		}

		if (inputReceiver.getButtonDown(KeyEvent.VK_1)) { equippedWeaponIndex = 0; }
		else if (inputReceiver.getButtonDown(KeyEvent.VK_2)) { equippedWeaponIndex = 1; }
	}

	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (sender.getID() == this.inputReceiver.getSenderId() && packet instanceof ClientLookAtPacket)
		{
			ClientLookAtPacket lookAtPacket = (ClientLookAtPacket)packet;

			System.out.println("received look at " + Compressor.scaleByteToFloat(lookAtPacket.rotation, -180, 180));
			transform.rotation = Compressor.scaleByteToFloat(lookAtPacket.rotation, -180, 180);
		}
	}
}
