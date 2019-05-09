package Util.Engine;


import java.awt.event.*;
import java.util.HashMap;


public class Input
{
	private static KeyAdapter adapter;

	private static HashMap<Integer, Boolean> buttonsDown;


	public static void init()
	{
		buttonsDown = new HashMap<>();

		adapter = new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				Input.buttonsDown.put(e.getKeyCode(), true);
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				Input.buttonsDown.put(e.getKeyCode(), false);
			}
		};

		Engine.canvas().getFrame().addKeyListener(adapter);
	}


	public static boolean getButtonDown(char key)
	{
		int keyCode = KeyEvent.getExtendedKeyCodeForChar(key);

		if (buttonsDown.containsKey(keyCode))
		{
			return buttonsDown.get(keyCode);
		}

		return false;
	}
}
