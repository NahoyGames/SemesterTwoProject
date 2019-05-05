package Util.Engine;

import Surviv.SurvivGameScene;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.TimerTask;

public class Engine
{
	/** CONFIGURATIONS **/
	public static final String WINDOW_NAME = "My Game";
	public static final int FRAMES_PER_SECOND = 1200;
	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 500;


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
		Time.step();
		Timer timer = new Timer(1000 / FRAMES_PER_SECOND, (ActionEvent e) ->
		{
			if (!isPlaying)
			{
				loadedScene.onApplicationQuit();
				System.exit(0);
				return;
			}

			loadedScene.update();
			loadedScene.drawFrame(canvas);

			Time.step();
		});
		timer.setRepeats(true);
		timer.start();



		/*isPlaying = true;
		long nextTick = System.currentTimeMillis();
		while (isPlaying)
		{
			loadedScene.update();
			loadedScene.draw(canvas.getRenderBuffer(), null);
			canvas.repaint();

			Time.step();

			nextTick += 1000 / (long) FRAMES_PER_SECOND;
			long sleepTime = nextTick - System.currentTimeMillis();
			if (sleepTime >= 0)
			{
				try
				{
					Thread.sleep(sleepTime);
				} catch (InterruptedException e)
				{
					continue;
				}
			}
			else
			{
				continue; // Running behind!!
			}
		}

		loadedScene.onApplicationQuit();
		System.exit(0);*/
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
