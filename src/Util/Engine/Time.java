package Util.Engine;

public class Time
{

	private static long lastTime;


	public static void step()
	{
		lastTime = System.currentTimeMillis();
	}


	/**
	 * Delta time between last frame and current frame
	 * @return Milliseconds elapsed
	 */
	public static float deltaTime()
	{
		return (System.currentTimeMillis() - lastTime);
	}


	public static float deltaTime(boolean inMilliseconds)
	{
		return inMilliseconds ? deltaTime() / 1000f : deltaTime();
	}

}
