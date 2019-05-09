package Util.Engine;

import Util.Engine.Networking.Client.ClientNetManager;
import Util.Engine.Networking.GenericNetManager;
import Util.Engine.Networking.Server.ServerNetManager;
import com.esotericsoftware.minlog.Log;

import javax.swing.Timer;
import java.awt.event.ActionEvent;

public class Engine
{
	// Config
	private static EngineConfiguration config;

	// Scenes
	private static Scene loadedScene;

	// Application
	private static boolean isPlaying;

	// Graphics
	private static Canvas canvas;

	// Network
	private static GenericNetManager netManager;


	public static void init(EngineConfiguration config)
	{
		Engine.config = config;
		Log.set(Log.LEVEL_DEBUG);

		// Engine
		if (!config.HEADLESS_MODE)
		{
			canvas = new Canvas(config.WINDOW_WIDTH, config.WINDOW_HEIGHT, config.WINDOW_NAME);
			Input.init();
		}

		// Networking
		if (config.IS_SERVER_BUILD)
		{
			netManager = new ServerNetManager(config.TCP_PORT, config.UDP_PORT, config.REGISTERED_PACKETS);
		}
		else
		{
			netManager = new ClientNetManager(config.SERVER_IP, config.TCP_PORT, config.UDP_PORT, config.REGISTERED_PACKETS);
		}

		// Scene
		loadScene(config.DEFAULT_SCENE);

		// Game Loop
		isPlaying = true;
		Time.step();
		Timer timer = new Timer(1000 / config.FRAMES_PER_SECOND, (ActionEvent e) ->
		{
			if (!isPlaying)
			{
				loadedScene.onApplicationQuit();
				System.exit(0);
				return;
			}

			loadedScene.update();

			if (!config.HEADLESS_MODE)
			{
				loadedScene.drawFrame(canvas);
			}

			Time.step();
		});

		// Start Game Loop
		timer.setRepeats(true);
		timer.start();
	}


	public static void loadScene(String name)
	{
		if (loadedScene != null)
		{
			loadedScene.onSceneUnload();
		}

		loadedScene = config.REGISTERED_SCENES.get(name);
		loadedScene.onSceneLoad();
	}


	public static Scene scene() { return loadedScene; }

	public static GenericNetManager netManager() { return netManager; }

	public static EngineConfiguration config() { return config; }

	public static Canvas canvas() { return canvas; }

	public static void quit()
	{
		isPlaying = false;
	}
}
