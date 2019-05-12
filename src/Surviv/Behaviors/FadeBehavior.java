package Surviv.Behaviors;


import Util.Engine.GameBehavior;
import Util.Engine.GameEntity;
import Util.Engine.Time;


public class FadeBehavior extends GameBehavior
{
	private GameEntity entity;

	private float waitTimer = 0f;
	private boolean destroyOnComplete = false;

	private boolean isScheduled = false;
	private float deltaAlphaPerSecond;


	public FadeBehavior(GameEntity entity)
	{
		this.entity = entity;
	}


	public void scheduleFade(float duration, float wait, boolean destroyOnComplete)
	{
		deltaAlphaPerSecond = entity.getAlpha() / duration;
		this.waitTimer = wait;
		this.destroyOnComplete = destroyOnComplete;

		isScheduled = true;
	}


	@Override
	public void update()
	{
		super.update();

		if (!isScheduled)
		{
			return;
		}

		if (waitTimer > 0)
		{
			waitTimer -= Time.deltaTime(true);
		}
		else
		{
			entity.setAlpha(Math.max(0, entity.getAlpha() - deltaAlphaPerSecond * Time.deltaTime(true)));

			if (entity.getAlpha() <= 0.01f)
			{
				if (destroyOnComplete)
				{
					entity.getScene().removeEntity(entity);
				}

				isScheduled = false;
			}
		}
	}
}
