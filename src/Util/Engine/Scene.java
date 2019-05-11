package Util.Engine;

import Util.Engine.Networking.INetworkListener;
import Util.Engine.Networking.NetGameEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scene implements IEngineEventListener
{
	private String name;

	private List<GameEntity> entities;
	private Camera activeCamera;


	public Scene(String name)
	{
		this.name = name;
		this.entities = new ArrayList<>();

		// Camera ~ Only MUST have entity
		// Also adds to array directly instead of using addEntity() to avoid double-callbacks
		entities.add(activeCamera = new Camera(this, new Color(105, 116, 136)));
	}


	public void addEntity(GameEntity entity)
	{
		entities.add(entity);
		entities.sort(Comparator.comparing(IDrawable::getLayer)); // Sorts by layer

		// Network
		if (entity instanceof NetGameEntity)
		{
			System.out.println("Spawned a network entity");
			Engine.netManager().addNetEntity(((NetGameEntity)entity).getNetworkId(), (NetGameEntity) entity);
		}

		entity.start();
	}


	public void removeEntity(GameEntity entity)
	{
		entity.onDisable();
		entities.remove(entity);

		if (entity instanceof INetworkListener)
		{
			Engine.netManager().removeListener((INetworkListener)entity);
		}
	}


	public void drawFrame(Canvas destinationCanvas)
	{
		activeCamera.renderFrame((List<IDrawable>)(List<? extends IDrawable>) entities, destinationCanvas);
		destinationCanvas.repaint();
	}


	public Camera camera() { return activeCamera; }


	@Override
	public void onSceneLoad()
	{
		for (GameEntity entity : entities)
		{
			entity.onSceneLoad();
		}

		for (GameEntity entity : entities)
		{
			entity.start();
		}
	}


	@Override
	public void start() { }


	@Override
	public void update()
	{
		// toArray to avoid ConcurrentModificationException's
		for (GameEntity entity : entities.toArray(new GameEntity[entities.size()]))
		{
			entity.update();
		}
	}


	@Override
	public void onDisable() { }


	@Override
	public void onSceneUnload()
	{
		for (GameEntity entity : entities)
		{
			entity.onSceneUnload();
		}
	}


	@Override
	public void onApplicationQuit()
	{
		for (GameEntity entity : entities)
		{
			entity.onApplicationQuit();
		}
	}
}
