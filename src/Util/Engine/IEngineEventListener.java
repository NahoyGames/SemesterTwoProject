package Util.Engine;

public interface IEngineEventListener
{
	void onSceneLoad();

	void awake();

	void start();

	void update();

	void onDisable();

	void onSceneUnload();

	void onApplicationQuit();
}
