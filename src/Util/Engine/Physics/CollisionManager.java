package Util.Engine.Physics;


import Util.Engine.Scene;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager
{
	private List<Collider> colliders, staticColliders, dynamicColliders; // No need to compare static to static colliders

	private Scene scene;


	public CollisionManager(Scene scene)
	{
		this.scene = scene;

		colliders = new ArrayList<>();
		staticColliders = new ArrayList<>();
		dynamicColliders = new ArrayList<>();
	}

	public void step()
	{
		//Collider[] dynamics = dynamicColliders.toArray(new Collider[dynamicColliders.size()]);
		for (int d = 0; d < dynamicColliders.size(); d++)
		{
			// Dynamic to static
			for (Collider staticCol : staticColliders.toArray(new Collider[staticColliders.size()]))
			{
				CollisionInfo collision = dynamicColliders.get(d).hasCollisionWith(staticCol);

				if (collision != null)
				{
					dynamicColliders.get(d).onCollision(staticCol, collision);
					staticCol.onCollision(dynamicColliders.get(d), collision);
				}
			}

			// Dynamic to dynamic
			for (int d2 = d + 1; d2 < dynamicColliders.size(); d2++)
			{
				Collider one = dynamicColliders.get(d);
				Collider two = dynamicColliders.get(d2);

				CollisionInfo collision = one.hasCollisionWith(two);

				if (collision != null)
				{
					one.onCollision(two, collision);
					two.onCollision(one, collision);
				}
			}
		}
	}


	public void addCollider(Collider collider)
	{
		colliders.add(collider);

		if (collider.isStatic)
		{
			staticColliders.add(collider);
		}
		else
		{
			dynamicColliders.add(collider);
		}
	}


	public void removeCollider(Collider collider)
	{
		colliders.remove(collider);

		if (collider.isStatic)
		{
			staticColliders.remove(collider);
		}
		else
		{
			dynamicColliders.remove(collider);
		}

		scene.camera().removeDebugDrawable(collider);
	}
}
