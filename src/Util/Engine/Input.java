package Util.Engine;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.EnumMap;


public class Input implements KeyListener, MouseMotionListener
{
	private EnumMap<InputAxes, Float> inputs;


	public Input(JFrame frame)
	{
		frame.addKeyListener(this);
		frame.addMouseMotionListener(this);

		inputs = new EnumMap<>(InputAxes.class);

		// Init inputs
		for (InputAxes i : InputAxes.values())
		{
			inputs.put(i, new Float(0));
		}
	}


	public float getAxis(InputAxes axis)
	{
		return inputs.get(axis);
	}


	public float getAxis(String axis)
	{
		return inputs.get(InputAxes.valueOf(axis));
	}


	@Override
	public void mouseDragged(MouseEvent e) { }


	@Override
	public void mouseMoved(MouseEvent e)
	{
		inputs.put(InputAxes.MouseX, (float)e.getX());
		inputs.put(InputAxes.MouseY, (float)e.getY());
	}


	@Override
	public void keyTyped(KeyEvent e)
	{
		setKey(e, true);
	}


	@Override
	public void keyPressed(KeyEvent e)
	{
		setKey(e, true);
	}


	@Override
	public void keyReleased(KeyEvent e)
	{
		setKey(e, false);
	}


	private void setKey(KeyEvent e, boolean toOn)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_W:
				inputs.put(InputAxes.Vertical, new Float(toOn ? 1 : 0));
				break;
			case KeyEvent.VK_S:
				inputs.put(InputAxes.Vertical, new Float(toOn ? -1 : 0));
				break;
			case KeyEvent.VK_A:
				inputs.put(InputAxes.Horizontal, new Float(toOn ? -1 : 0));
				break;
			case KeyEvent.VK_D:
				inputs.put(InputAxes.Horizontal, new Float(toOn ? 1 : 0));
				break;
		}
	}
}
