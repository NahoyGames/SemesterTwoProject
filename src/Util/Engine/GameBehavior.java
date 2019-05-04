package Util.Engine;

public abstract class GameBehavior implements IGameBehavior
{
	private GameEntity entity;


	public GameBehavior(GameEntity entity) { this.entity = entity; }

	public GameEntity entity() { return entity; }

	public void onSceneLoad() { }

	public void awake() { }

	public void start() { }

	public void update() { }

	public void onDisable() { }

	public void onSceneUnload() { }

	public void onApplicationQuit() { }
}
