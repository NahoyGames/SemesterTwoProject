package Util.Engine.Networking.Server;

import Util.Engine.Engine;
import Util.Engine.GameBehavior;
import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.ClientInputPacket;
import com.esotericsoftware.kryonet.Connection;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class ServerInputReceiver extends GameBehavior implements INetworkListener
{
	private Connection sender;
	private HashMap<Integer, Boolean> buttonsDown;


	public ServerInputReceiver(Connection sender)
	{
		this.sender = sender;
		this.buttonsDown = new HashMap<>();

		Engine.netManager().addListener(this);
	}


	public boolean getButtonDown(int keyCode)
	{
		if (buttonsDown.containsKey(keyCode))
		{
			return buttonsDown.get(keyCode);
		}

		return false;
	}


	@Override
	public void onReceivePacket(Connection sender, Packet packet)
	{
		if (sender.getID() == this.sender.getID() && packet instanceof ClientInputPacket)
		{
			ClientInputPacket inputPacket = (ClientInputPacket) packet;

			buttonsDown.put(inputPacket.keyCode, inputPacket.setOn);

			System.out.println("Received packet for input " + KeyEvent.getKeyText(inputPacket.keyCode));
		}
	}
}
