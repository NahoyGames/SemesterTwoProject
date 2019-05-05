package Util.Engine;

public abstract class EngineEventListener implements IEngineEventListener
{
	private GameEntity entity;


	public EngineEventListener(GameEntity entity) { this.entity = entity; }

	public GameEntity entity() { return entity; }

	public void onSceneLoad() { }

	public void awake() { }

	public void start() { }

	public void update() { }

	public void onDisable() { }

	public void onSceneUnload() { }

	public void onApplicationQuit() { }
}
