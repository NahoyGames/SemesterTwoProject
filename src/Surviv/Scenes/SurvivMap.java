package Surviv.Scenes;

import Surviv.Entities.Common.Barrel;
import Util.Engine.Physics.Colliders.CircleCollider;
import Util.Engine.Scene;
import Util.Math.Vec2f;


public class SurvivMap
{
	// Generates a new list of map items based off the *regretfully* hard-coded map
	// It's hard-coded because there's no way I'm gonna bother making a map editor
	public static void generateMap(Scene scene)
	{
		addBarrel(scene, new Vec2f(4, 6), 0.35f);
		addBarrel(scene, new Vec2f(40, 60), 0.35f);
	}


	private static void addBarrel(Scene scene, Vec2f pos, float scale)
	{
		scene.addEntity(new Barrel(scene)
		{
			{
				transform().position = pos;
				transform().scale = Vec2f.one().scale(scale);
				scene.collisionManager().addCollider(new CircleCollider(true, this, 50f * scale));
			}
		});
	}
}
