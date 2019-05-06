package Util.Engine;

public interface IEngineEventListener
{
	void onSceneLoad();

	void start();

	void update();

	void onDisable();

	void onSceneUnload();

	void onApplicationQuit();
}
