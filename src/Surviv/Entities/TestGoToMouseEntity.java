package Surviv.Entities;

import Util.Engine.Engine;
import Util.Engine.GameEntity;
import Util.Engine.Scene;
import Util.Math.Vec2f;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class TestGoToMouseEntity extends GameEntity
{
	public TestGoToMouseEntity(Scene scene)
	{
		super(scene, "/Assets/Sprites/character.png");

		transform.scale = new Vec2f(0.05f, 0.05f);

		Engine.canvas().getFrame().addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				transform.position = Engine.scene().camera().screenToWorldPoint(new Vec2f(e.getX(), e.getY()));
			}
		});
	}


	@Override
	public int getLayer()
	{
		return 0;
	}
}
