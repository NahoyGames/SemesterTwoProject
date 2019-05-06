package Surviv.Scenes.Server;

import Surviv.Entities.Server.*;
import Util.Engine.Engine;
import Util.Engine.Scene;

public class SurvivGameScene extends Scene
{
	public SurvivGameScene()
	{
		super("Surviv Game");
	}


	@Override
	public void onSceneLoad()
	{
	}


	@Override
	public void update()
	{
		if (Engine.input().getAxis("Horizontal") > 0)
		{
			System.out.println("Spawned an entity on server!");
			addEntity(new Player(this));
		}
	}
}
