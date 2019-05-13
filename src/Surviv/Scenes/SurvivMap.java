package Surviv.Scenes;

import Surviv.Entities.Environment.Barrel;
import Surviv.Entities.Environment.Crate;
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
		addCrate(scene, new Vec2f(100, 200), 0.15f);
		addCrate(scene, new Vec2f(-400, -200), 0.15f);
	}


	private static void addBarrel(Scene scene, Vec2f pos, float scale)
	{
		scene.addEntity(new Barrel(scene)
		{
			{
				transform().position = pos;
				transform().scale = Vec2f.one().scale(scale);
				scene.collisionManager().addCollider(new CircleCollider(true, this, 50f));
			}
		});
	}



	private static void addCrate(Scene scene, Vec2f pos, float scale)
	{
		scene.addEntity(new Crate(scene)
		{
			{
				transform.position = pos;
				transform.scale = Vec2f.one().scale(scale);
				scene.collisionManager().addCollider(new BoxCollider(true, this, Vec2f.one().scale(150f)));
			}
		});
	}
}
