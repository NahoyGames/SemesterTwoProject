package Surviv.Scenes;

import Surviv.Entities.Environment.Barrel;
import Surviv.Entities.Environment.Crate;
import Surviv.Entities.Server.LootCrate;
import Util.Engine.Engine;
import Util.Engine.Physics.Colliders.BoxCollider;
import Util.Engine.Physics.Colliders.CircleCollider;
import Util.Engine.Scene;
import Util.Math.Vec2f;


public class SurvivMap
{
	// Generates a new list of map items based off the *regretfully* hard-coded map
	// It's hard-coded because there's no way I'm gonna bother making a map editor
	public static void generateMap(Scene scene)
	{
		addBarrel(scene, new Vec2f(60, -30), 0.35f);
		addBarrel(scene, new Vec2f(40, 60), 0.35f);

		for (int i = 0; i < 30; i++)
		{
			addCrate(scene, new Vec2f((float)(Math.random() - 0.5) * 2000, (float)(Math.random() - 0.5) * 2000));
		}
	}


	private static void addBarrel(Scene scene, Vec2f pos, float scale)
	{
		scene.addEntity(new Barrel(scene)
		{
			{
				transform().position = pos;
				transform().scale = Vec2f.one().scale(scale);
			}
		});
	}



	private static void addCrate(Scene scene, Vec2f pos)
	{
		if (Engine.config() == null || !Engine.config().IS_SERVER_BUILD) { return; }

		scene.addEntity(new LootCrate(scene)
		{
			{
				transform.position = pos;
			}
		});
	}
}
