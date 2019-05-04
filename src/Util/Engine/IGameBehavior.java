package Util.Engine;

public interface IGameBehavior
{
	void onSceneLoad();

	void awake();

	void start();

	void update();

	void onDisable();

	void onSceneUnload();

	void onApplicationQuit();
}
