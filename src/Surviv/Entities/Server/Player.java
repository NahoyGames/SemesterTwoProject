package Surviv.Entities.Server;


import Surviv.Behaviors.Health.ServerHealthBehavior;
import Surviv.Behaviors.ServerWeaponBehavior;
import Surviv.Behaviors.Weapons.Ak47;
import Surviv.Behaviors.Weapons.DesertEagle;
import Surviv.Behaviors.Weapons.MachineGun;
import Surviv.Behaviors.Weapons.Shotgun;
import Surviv.Entities.Environment.IEnvironment;
import Surviv.Networking.Packets.AmmoChangePacket;
import Surviv.Networking.Packets.ClientLookAtPacket;
import Surviv.Networking.Packets.PlayerChangeInventoryPacket;
import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
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
import Util.Math.NetInt32;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;


public class Player extends ServerGameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/character.png";

	private static int playersAlive = 0;

	private Connection connection;

	private ServerInputReceiver inputReceiver;

	private ServerHealthBehavior health;
	private Collider collider;

	private int equippedWeaponIndex;
	private ServerWeaponBehavior[] inventory;
	private NetInt32 ammo;


	public Player(Scene scene, Connection connection)
	{
		super(scene, SPRITE_PATH);

		// Increment alive players
		playersAlive++;

		// Initial transforms
		transform.scale = Vec2f.one().scale(0.15f);

		// Assign client
		this.connection = connection;
		connection.sendTCP(new LinkClientToEntityPacket(getNetworkId()));

		// Initial behaviors
		addBehavior(new ServerNetTransform(this, 9));
		addBehavior(inputReceiver = new ServerInputReceiver(connection));
		addBehavior(health = new ServerHealthBehavior(this, 100));

		// Ammo
		ammo = new NetInt32(100, scene, AmmoChangePacket.class);

		// Inventory
		inventory = new ServerWeaponBehavior[]
				{
						(ServerWeaponBehavior) addBehavior(new Ak47(this, ammo)),
						(ServerWeaponBehavior) addBehavior(new Shotgun(this, ammo)),
						(ServerWeaponBehavior) addBehavior(new DesertEagle(this, ammo)),
						(ServerWeaponBehavior) addBehavior(new MachineGun(this, ammo))
				};

		// Hitbox
		addBehavior(collider = new CircleCollider(false, this, 100)
		{
			@Override
			public void onCollision(Collider other, CollisionInfo info)
			{
				super.onCollision(other, info);

				if (other.getEntity() instanceof IEnvironment && !(Float.isNaN(info.normal.x) && Float.isNaN(info.normal.y)))
				{
					Player.this.transform.position = Player.this.transform.position.subtract(info.normal.scale(info.dist));
				}
				else if (other.getEntity() instanceof AmmoItem)
				{
					if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).PICK_UP_KEY))
					{
						if (other.getEntity() instanceof AmmoItem)
						{
							other.getEntity().getScene().removeEntity(other.getEntity());

							ammo.value += 50;
						}
					}
				}
			}
		});
	}


	@Override
	protected void drawSprite(Graphics2D renderBuffer)
	{
		// Draw weapon
		renderBuffer.drawImage(inventory[equippedWeaponIndex].getGraphics(), 0, 0, null);

		// Draw sprite *above* weapon
		super.drawSprite(renderBuffer);
	}


	@Override
	protected void drawGui(Graphics2D renderBuffer)
	{
		super.drawGui(renderBuffer);

		health.drawHealthBar(renderBuffer, -30);

		// Ammo
		renderBuffer.setColor(Color.GREEN);
		renderBuffer.drawString(ammo.value + "", 10, 10);
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

		// Movement
		Vec2f movement = Vec2f.zero();

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration) Engine.config()).MOVE_LEFT_KEY))
		{
			movement.x -= 1;
		}

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).MOVE_RIGHT_KEY))
		{
			movement.x += 1;
		}

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).MOVE_UP_KEY))
		{
			movement.y += 1;
		}

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).MOVE_DOWN_KEY))
		{
			movement.y -= 1;
		}

		transform.position = transform.position.add(movement.normalized().scale(Time.deltaTime(true) * 200));

		// Inventory Slots
		if (inputReceiver.getButtonDown(Engine.config().MOUSE_KEYCODE))
		{
			inventory[equippedWeaponIndex].tryUse();
		}

		if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).EQUIP_1_KEY)) { sendReliable(new PlayerChangeInventoryPacket(getNetworkId(), (byte)(equippedWeaponIndex = 0))); }
		else if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).EQUIP_2_KEY)) { sendReliable(new PlayerChangeInventoryPacket(getNetworkId(), (byte)(equippedWeaponIndex = 1))); }
		else if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).EQUIP_3_KEY)) { sendReliable(new PlayerChangeInventoryPacket(getNetworkId(), (byte)(equippedWeaponIndex = 2))); }
		else if (inputReceiver.getButtonDown(((SurvivEngineConfiguration)Engine.config()).EQUIP_4_KEY)) { sendReliable(new PlayerChangeInventoryPacket(getNetworkId(), (byte)(equippedWeaponIndex = 3))); }
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


	@Override
	public void onDisable()
	{
		super.onDisable();

		playersAlive--;
	}


	public static int getPlayersAlive()
	{
		return playersAlive;
	}
}
