package Surviv.Entities.Common;

import Surviv.Entities.Server.Player;
import Util.Engine.GameEntity;
import Util.Engine.Scene;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class PlayersAliveDisplay extends GameEntity
{
	public PlayersAliveDisplay(Scene scene)
	{
		super(scene, null);
	}


	@Override
	protected void drawSprite(Graphics2D renderBuffer)
	{
		renderBuffer.setTransform(new AffineTransform());
		renderBuffer.setColor(Color.RED);
		renderBuffer.drawString("Players Remaining: " + Math.max(Player.getPlayersAlive(), Surviv.Entities.Client.Player.getPlayersAlive()), 10, 10);
	}

	@Override
	public int getLayer()
	{
		return 0;
	}
}
