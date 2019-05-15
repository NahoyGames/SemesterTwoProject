package Surviv.Entities.Client;


import Surviv.Behaviors.Health.ClientHealthBehavior;
import Surviv.Behaviors.WeaponBehavior;
import Surviv.Behaviors.Weapons.Ak47;
import Surviv.Behaviors.Weapons.DesertEagle;
import Surviv.Behaviors.Weapons.MachineGun;
import Surviv.Behaviors.Weapons.Shotgun;
import Surviv.Entities.Environment.IEnvironment;
import Surviv.Networking.Packets.ClientLookAtPacket;
import Surviv.Networking.Packets.PlayerChangeInventoryPacket;
import Surviv.Networking.Packets.TestRequestPacket;
import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Client.ClientInputSender;
import Util.Engine.Networking.Client.ClientNetManager;
import Util.Engine.Networking.Client.ClientNetTransform;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.LinkClientToEntityPacket;
import Util.Engine.Physics.Collider;
import Util.Engine.Physics.Colliders.CircleCollider;
import Util.Engine.Physics.CollisionInfo;
import Util.Engine.Scene;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class Player extends ClientGameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/character.png";
	private boolean hasLocalPlayerAuthority;

	private int equippedWeaponIndex;
	private WeaponBehavior[] inventory;

	private ClientHealthBehavior health;
	private Collider collider;


	public Player(Scene scene, short networkId)
	{
		super(scene, SPRITE_PATH, networkId);

		// Initial transform
		transform.scale = Vec2f.one().scale(0.15f);

		// Initial behaviors
		addBehavior(new ClientNetTransform(this));
		addBehavior(new ClientInputSender(new int[]
				{
						((SurvivEngineConfiguration)Engine.config()).MOVE_LEFT_KEY,
						((SurvivEngineConfiguration)Engine.config()).MOVE_DOWN_KEY,
						((SurvivEngineConfiguration)Engine.config()).MOVE_RIGHT_KEY,
						((SurvivEngineConfiguration)Engine.config()).MOVE_UP_KEY,
						((SurvivEngineConfiguration)Engine.config()).MOUSE_KEYCODE,
						((SurvivEngineConfiguration)Engine.config()).EQUIP_1_KEY,
						((SurvivEngineConfiguration)Engine.config()).EQUIP_2_KEY,
						((SurvivEngineConfiguration)Engine.config()).EQUIP_3_KEY,
						((SurvivEngineConfiguration)Engine.config()).EQUIP_4_KEY
				}));
		addBehavior(health = new ClientHealthBehavior(this, 100));

		// Inventory
		inventory = new WeaponBehavior[]
				{
						(WeaponBehavior) addBehavior(new Ak47(this)),
						(WeaponBehavior) addBehavior(new Shotgun(this)),
						(WeaponBehavior) addBehavior(new DesertEagle(this)),
						(WeaponBehavior) addBehavior(new MachineGun(this))
				};

		// Hitbox
		scene.collisionManager().addCollider(collider = new CircleCollider(false, this, 100)
		{
			@Override
			public void onCollision(Collider other, CollisionInfo info)
			{
				super.onCollision(other, info);

				if (other.getEntity() instanceof IEnvironment && !(Float.isNaN(info.normal.x) && Float.isNaN(info.normal.y)))
				{
					Player.this.transform.position = Player.this.transform.position.subtract(info.normal.scale(info.dist));
				}
			}
		});
	}


	public boolean isLocalPlayer() { return hasLocalPlayerAuthority; }


	private void sendLookAt(Vec2f mousePos)
	{
		float rot = transform.getLookAtAngle(mousePos);

		((ClientNetManager) Engine.netManager()).sendUnreliable(new ClientLookAtPacket(rot));
	}


	@Override
	protected void drawSprite(Graphics2D renderBuffer)
	{
		renderBuffer.drawImage(inventory[equippedWeaponIndex].getGraphics(), 0, 0, null);

		super.drawSprite(renderBuffer);
	}


	@Override
	protected void drawGui(Graphics2D renderBuffer)
	{
		super.drawGui(renderBuffer);

		health.drawHealthBar(renderBuffer, -30);
	}

	@Override
	public int getLayer()
	{
		return 0;
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (packet instanceof TestRequestPacket)
		{
			System.out.println(((TestRequestPacket)packet).message);
		}
		else if (packet instanceof LinkClientToEntityPacket)
		{
			if (((LinkClientToEntityPacket)packet).entityNetworkId == getNetworkId())
			{
				hasLocalPlayerAuthority = true;
				System.out.println("I, netEntity#" + getNetworkId() + ", have local player authority!");

				// Look at mouse(not a behavior because it's player specific)
				Engine.canvas().getFrame().addMouseMotionListener(new MouseMotionAdapter()
				{
					@Override
					public void mouseMoved(MouseEvent e)
					{
						sendLookAt(Engine.scene().camera().screenToWorldPoint(new Vec2f(e.getX(), e.getY())));
					}

					@Override
					public void mouseDragged(MouseEvent e)
					{
						sendLookAt(Engine.scene().camera().screenToWorldPoint(new Vec2f(e.getX(), e.getY())));
					}
				});
			}
		}
		else if (packet instanceof PlayerChangeInventoryPacket)
		{
			PlayerChangeInventoryPacket inventoryPacket = (PlayerChangeInventoryPacket)packet;

			if (inventoryPacket.entityNetworkId == getNetworkId())
			{
				equippedWeaponIndex = (int) inventoryPacket.inventoryIndex;
			}
		}
	}


	@Override
	public void update()
	{
		super.update();

		if (isLocalPlayer())
		{
			Engine.scene().camera().transform().position = this.transform.position;
		}
	}


	@Override
	public void onDisable()
	{
		super.onDisable();

		scene.collisionManager().removeCollider(collider);
	}
}
