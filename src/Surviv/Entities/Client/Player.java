package Surviv.Entities.Client;


import Surviv.Networking.Packets.ClientLookAtPacket;
import Surviv.Networking.Packets.TestRequestPacket;
import Surviv.SurvivEngineConfiguration;
import Util.Engine.Engine;
import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Client.ClientInputSender;
import Util.Engine.Networking.Client.ClientNetManager;
import Util.Engine.Networking.Client.ClientNetTransform;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.LinkClientToEntityPacket;
import Util.Engine.Scene;
import Util.Math.Vec2f;
import com.esotericsoftware.kryonet.Connection;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class Player extends ClientGameEntity
{
	private static final String SPRITE_PATH = "/Assets/Sprites/character.png";
	private boolean hasLocalPlayerAuthority;


	public Player(Scene scene, short networkId)
	{
		super(scene, SPRITE_PATH, networkId);

		// Initial transform
		transform.scale = new Vec2f(0.1f, 0.1f);

		// Initial behaviors
		addBehavior(new ClientNetTransform(this));
		addBehavior(new ClientInputSender(new int[]
				{
						((SurvivEngineConfiguration)Engine.config()).MOVE_LEFT_KEY,
						((SurvivEngineConfiguration)Engine.config()).MOVE_DOWN_KEY,
						((SurvivEngineConfiguration)Engine.config()).MOVE_RIGHT_KEY,
						((SurvivEngineConfiguration)Engine.config()).MOVE_UP_KEY,
						KeyEvent.VK_SPACE
				}));
	}


	public boolean isLocalPlayer() { return hasLocalPlayerAuthority; }


	private void sendLookAt(Vec2f mousePos)
	{
		float rot = transform.getLookAtAngle(mousePos);

		((ClientNetManager) Engine.netManager()).sendUnreliable(new ClientLookAtPacket(rot));
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
						System.out.println("Sent look at");
						sendLookAt(Engine.scene().camera().screenToWorldPoint(new Vec2f(e.getX(), e.getY())));
					}
				});
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
}
