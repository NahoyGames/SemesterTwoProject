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
		super.onSceneLoad();
	}


	@Override
	public void update()
	{
		super.update();

		if (Engine.input().getAxis("Horizontal") > 0)
		{
			addEntity(new Player(this));
		}
	}
}
