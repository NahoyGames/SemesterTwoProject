package Util.Engine.Networking.Client;

import Util.Arrays.Arrays;
import Util.Engine.Engine;
import Util.Engine.GameBehavior;
import Util.Engine.Networking.Packets.ClientInputPacket;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ClientInputSender extends GameBehavior
{
	private KeyAdapter adapter;


	public ClientInputSender(int[] keyCodesToSend)
	{
		adapter = new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (Arrays.contains(keyCodesToSend, e.getKeyCode()))
				{
					((ClientNetManager)Engine.netManager()).sendReliable(new ClientInputPacket(e.getKeyCode(), true));
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (Arrays.contains(keyCodesToSend, e.getKeyCode()))
				{
					System.out.println("KEY RELEASED");
					((ClientNetManager)Engine.netManager()).sendReliable(new ClientInputPacket(e.getKeyCode(), false));
				}
			}
		};

		Engine.canvas().getFrame().addKeyListener(adapter);
	}
}
