package Surviv;

import Surviv.Scenes.Client.*;
import Util.Engine.Engine;
import Util.Engine.Scene;

import java.util.HashMap;

public class ClientBuild
{
	public static void main(String[] args)
	{
		Engine.init(new SurvivEngineConfiguration()
		{
			{
				WINDOW_NAME = "Surviv Client";
				VERSION = "0.0.1";
				SERVER_IP = "10.220.19.82";
				REGISTERED_SCENES = new HashMap<String, Scene>()
				{
					{
						put("Game", new SurvivGameScene());
					}
				};
				DEFAULT_SCENE = "Game";
				IS_SERVER_BUILD = false;
			}
		});
	}
}
