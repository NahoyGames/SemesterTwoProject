package Util.Engine.Networking.Client;

import Util.Arrays.Arrays;
import Util.Engine.Engine;
import Util.Engine.GameBehavior;
import Util.Engine.Networking.Packets.ClientInputPacket;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ClientInputSender extends GameBehavior
{
	private KeyAdapter keyAdapter;
	private MouseAdapter mouseAdapter;


	public ClientInputSender(int[] keyCodesToSend)
	{
		keyAdapter = new KeyAdapter()
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
					((ClientNetManager)Engine.netManager()).sendReliable(new ClientInputPacket(e.getKeyCode(), false));
				}
			}
		};

		Engine.canvas().getFrame().addKeyListener(keyAdapter);

		mouseAdapter = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (Arrays.contains(keyCodesToSend, Engine.config().MOUSE_KEYCODE))
				{
					//((ClientNetManager) Engine.netManager()).sendReliable(new ClientInputPacket(Engine.config().MOUSE_KEYCODE, true));
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				if (Arrays.contains(keyCodesToSend, Engine.config().MOUSE_KEYCODE))
				{
					((ClientNetManager) Engine.netManager()).sendReliable(new ClientInputPacket(Engine.config().MOUSE_KEYCODE, true));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (Arrays.contains(keyCodesToSend, Engine.config().MOUSE_KEYCODE))
				{
					((ClientNetManager) Engine.netManager()).sendReliable(new ClientInputPacket(Engine.config().MOUSE_KEYCODE, false));
				}
			}
		};

		Engine.canvas().getFrame().addMouseListener(mouseAdapter);
	}
}
