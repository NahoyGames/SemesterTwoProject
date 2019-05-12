package Util.Engine.Physics;

import Util.Engine.Physics.Colliders.BoxCollider;
import Util.Engine.Physics.Colliders.PointCollider;
import Util.Engine.Physics.Colliders.CircleCollider;
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
				CollisionInfo collision = hasCollision(dynamicCol, staticCol);

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


	public CollisionInfo hasCollision(Collider a, Collider b)
	{
		if (a instanceof CircleCollider)
		{
			if (b instanceof PointCollider)
			{
				return ColliderUtil.pointCircleCollision(b.getOrigin(), a.getOrigin(), ((CircleCollider)a).getRadius());
			}
			else if (b instanceof CircleCollider)
			{
				return ColliderUtil.circleCircleCollision(a.getOrigin(), ((CircleCollider) a).getRadius(), b.getOrigin(), ((CircleCollider) b).getRadius());
			}
			else if (b instanceof BoxCollider)
			{
				return ColliderUtil.boxCircleCollision(b.getOrigin(), ((BoxCollider) b).getHalfSize(), a.getOrigin(), ((CircleCollider) a).getRadius());
			}
		}
		else if (a instanceof BoxCollider)
		{
			if (b instanceof PointCollider)
			{
				return ColliderUtil.pointBoxCollision(b.getOrigin(), a.getOrigin(), ((BoxCollider) a).getHalfSize());
			}
			else if (b instanceof CircleCollider)
			{
				return ColliderUtil.boxCircleCollision(a.getOrigin(), ((BoxCollider) a).getHalfSize(), b.getOrigin(), ((CircleCollider) b).getRadius());
			}
			else if (b instanceof BoxCollider)
			{
				return null; // TODO Box-Box collision
			}
		}
		else if (a instanceof PointCollider)
		{
			if (b instanceof CircleCollider)
			{
				return ColliderUtil.pointCircleCollision(a.getOrigin(), b.getOrigin(), ((CircleCollider) b).getRadius());
			}
			else if (b instanceof BoxCollider)
			{
				return ColliderUtil.pointBoxCollision(a.getOrigin(), b.getOrigin(), ((BoxCollider) b).getHalfSize());
			}
		}

		return null;
	}
}
