package Util.Engine;

import Surviv.SurvivGameScene;

import java.util.HashMap;

public class Engine
{
	/** CONFIGURATIONS **/
	public static final String WINDOW_NAME = "My Game";
	public static final int FRAMES_PER_SECOND = 60;
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 400;


	// Scenes
	private static HashMap<String, Scene> scenes;
	private static Scene loadedScene;

	// Application
	private static boolean isPlaying;

	// Graphics
	private static Canvas canvas;

	// Input
	private static Input input;


	public static void main(String[] args)
	{
		scenes = new HashMap<String, Scene>()
		{
			{
				put("Game", new SurvivGameScene());
			}
		};

		loadScene("Game");
		canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_NAME);
		input = new Input(canvas.getFrame());


		// Game Loop
		isPlaying = true;
		while (isPlaying)
		{
			loadedScene.update();
			loadedScene.draw(canvas.getRenderBuffer(), null);
			canvas.repaint();

			Time.step();

			try
			{
				Thread.sleep(1000 / (long) FRAMES_PER_SECOND);
			}
			catch (InterruptedException e ) { continue; }
		}

		loadedScene.onApplicationQuit();
		System.exit(0);
	}


	public static void loadScene(String name)
	{
		if (loadedScene != null)
		{
			loadedScene.onSceneUnload();
		}

		loadedScene = scenes.get(name);
		loadedScene.onSceneLoad();
	}


	public static Input input() { return input; }

	public static Scene scene() { return loadedScene; }


	public static void quit()
	{
		isPlaying = false;
	}
}
