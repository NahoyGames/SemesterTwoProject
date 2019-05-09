package Surviv.Scenes.Client;

import Surviv.Entities.TestGoToMouseEntity;
import Util.Engine.Scene;

public class SurvivGameScene extends Scene
{
	public SurvivGameScene()
	{
		super("Surviv.io");
	}

	@Override
	public void onSceneLoad()
	{
		super.onSceneLoad();

		addEntity(new TestGoToMouseEntity(this));
	}
}
