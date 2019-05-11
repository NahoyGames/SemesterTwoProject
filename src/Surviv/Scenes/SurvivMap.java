package Surviv.Scenes;

import Surviv.Entities.Common.Barrel;
import Util.Engine.Scene;
import Util.Math.Vec2f;


public class SurvivMap
{
	// Generates a new list of map items based off the *regretfully* hard-coded map
	// It's hard-coded because there's no way I'm gonna bother making a map editor
	public static void generateMap(Scene scene)
	{

		scene.addEntity(new Barrel(scene)
		{
			{
				transform().position = new Vec2f(4, 6);
				transform().scale = Vec2f.one().scale(0.35f);
			}
		});


		scene.addEntity(new Barrel(scene)
		{
			{
				transform().position = new Vec2f(40, 60);
				transform().scale = Vec2f.one().scale(0.35f);
			}
		});
	}
}
