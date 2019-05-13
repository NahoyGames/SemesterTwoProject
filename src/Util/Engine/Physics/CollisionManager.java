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
		for (Collider dynamicCol : dynamicColliders.toArray(new Collider[dynamicColliders.size()]))
		{
			for (Collider staticCol : staticColliders.toArray(new Collider[staticColliders.size()]))
			{
				CollisionInfo collision = dynamicCol.hasCollisionWith(staticCol);

				if (collision != null)
				{
					dynamicCol.onCollision(staticCol, collision);
					staticCol.onCollision(dynamicCol, collision);
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
