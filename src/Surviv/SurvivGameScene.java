package Surviv;

import Surviv.Entities.Player;
import Util.Engine.GameEntity;
import Util.Engine.Scene;

public class SurvivGameScene extends Scene
{
	public SurvivGameScene()
	{
		super("Surviv.io");

		addEntity(new Player(this));
	}
}
