package Surviv.Scenes.Server;

import Surviv.Entities.Server.*;
import Surviv.Scenes.SurvivMap;
import Util.Engine.Engine;
import Util.Engine.Input;
import Util.Engine.Networking.Server.ServerNetManager;
import Util.Engine.Scene;
import Util.Engine.Time;
import com.esotericsoftware.kryonet.Connection;

import java.awt.event.KeyEvent;


public class SurvivGameScene extends Scene
{
	private boolean gameStarted = false;


	public SurvivGameScene()
	{
		super("Surviv Game");

		SurvivMap.generateMap(this);
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

		if (Input.getButtonDown(' ') && !gameStarted)
		{
			for (Connection c : ((ServerNetManager)Engine.netManager()).getConnections())
			{
				addEntity(new Player(this, c));
			}

			gameStarted = true;
		}

		if (Input.getButtonDown(KeyEvent.VK_UP))
		{
			camera().setFov(camera().getFov() + 0.001f * Time.deltaTime());
		}
		else if (Input.getButtonDown(KeyEvent.VK_DOWN))
		{
			camera().setFov(camera().getFov() - 0.001f * Time.deltaTime());
		}
	}
}
