package Util.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * A simple class which initializes a window, from which you can draw pixels individually.
 */
public class Canvas
{

	private int width;
	private int height;

	private Panel panel;
	private JFrame frame;


	public Canvas(int width, int height, String name, Input input)
	{
		this.width = width;
		this.height = height;
		this.panel = new Panel(width, height);

		frame = new JFrame(name);

		frame.addKeyListener(input);
		frame.addMouseMotionListener(input);
		frame.setFocusable(true);
		frame.requestFocus();
		panel.requestFocus();

		frame.setSize(width, height);
		frame.setResizable(true);
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public JFrame getFrame()
	{
		return frame;
	}


	public JPanel getPanel() { return panel; }


	public Graphics2D getRenderBuffer()
	{
		return (Graphics2D) panel.bufferedImage.getGraphics();
	}


	public void repaint()
	{
		panel.repaint();
	}


	private class Panel extends JPanel
	{
		public BufferedImage bufferedImage;


		public Panel(int width, int height)
		{
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}


		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g;
			g2D.drawImage(bufferedImage, null, null);
		}
	}

}
