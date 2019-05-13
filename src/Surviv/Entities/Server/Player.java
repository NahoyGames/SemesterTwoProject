package Surviv.Entities.Server;


import Surviv.Behaviors.WeaponBehavior;
import Surviv.Behaviors.Weapons.Ak47;
import Surviv.Behaviors.Weapons.DesertEagle;
import Surviv.Behaviors.Weapons.MachineGun;
import Surviv.Behaviors.Weapons.Shotgun;
import Surviv.Networking.Packets.ClientLookAtPacket;
import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
import Util.Engine.IDrawable;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.LinkClientToEntityPacket;
import Util.Engine.Networking.Server.ServerGameEntity;
import Util.Engine.Networking.Server.ServerInputReceiver;
import Util.Engine.Networking.Server.ServerNetTransform;
import Util.Engine.Physics.Collider;
import Util.Engine.Physics.Colliders.CircleCollider;
import Util.Engine.Physics.CollisionInfo;
import Util.Engine.Scene;
import Util.Engine.Time;
import Util.Math.Compressor;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;


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
		transform.scale = Vec2f.one().scale(0.15f);

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
						(WeaponBehavior) addBehavior(new Shotgun(this)),
						(WeaponBehavior) addBehavior(new DesertEagle(this)),
						(WeaponBehavior) addBehavior(new MachineGun(this))
				};

		// Hitbox
		scene.collisionManager().addCollider(new CircleCollider(false, this, 100)
		{
			@Override
			public void onCollision(Collider other, CollisionInfo info)
			{
				super.onCollision(other, info);

				if (!(Float.isNaN(info.normal.x) && Float.isNaN(info.normal.y)))
				{
					Player.this.transform.position = Player.this.transform.position.subtract(info.normal.scale(info.dist));
				}
			}
		});
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

		if (inputReceiver.getButtonDown(Engine.config().MOUSE_KEYCODE))
		{
			inventory[equippedWeaponIndex].tryUse();
		}

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).EQUIP_1_KEY)) { equippedWeaponIndex = 0; }
		else if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).EQUIP_2_KEY)) { equippedWeaponIndex = 1; }
		else if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).EQUIP_3_KEY)) { equippedWeaponIndex = 2; }
		else if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).EQUIP_4_KEY)) { equippedWeaponIndex = 3; }
	}

	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (sender.getID() == this.inputReceiver.getSenderId() && packet instanceof ClientLookAtPacket)
		{
			ClientLookAtPacket lookAtPacket = (ClientLookAtPacket)packet;

			transform.rotation = Compressor.scaleByteToFloat(lookAtPacket.rotation, -180, 180);
		}
	}
}
