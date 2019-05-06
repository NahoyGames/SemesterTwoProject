package Surviv;

import Surviv.Scenes.Server.*;
import Util.Engine.Engine;
import Util.Engine.Scene;

import java.util.HashMap;

public class ServerBuild
{
	public static void main(String[] args)
	{
		Engine.init(new SurvivEngineConfiguration()
		{
			{
				WINDOW_NAME = "Surviv Server";
				REGISTERED_SCENES = new HashMap<String, Scene>()
				{
					{
						put("Game", new SurvivGameScene());
					}
				};
				DEFAULT_SCENE = "Game";
				IS_SERVER_BUILD = true;
			}
		});
	}
}
