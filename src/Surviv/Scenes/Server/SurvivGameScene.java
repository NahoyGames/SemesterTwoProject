package Surviv.Scenes.Server;

import Surviv.Entities.Server.*;
import Util.Engine.Engine;
import Util.Engine.Input;
import Util.Engine.Networking.Server.ServerNetManager;
import Util.Engine.Scene;
import com.esotericsoftware.kryonet.Connection;


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

		if (Input.getButtonDown(' '))
		{
			for (Connection c : ((ServerNetManager)Engine.netManager()).getConnections())
			addEntity(new Player(this, c));
		}
	}
}
