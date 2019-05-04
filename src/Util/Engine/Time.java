package Util.Engine;

public class Time
{

	private static long lastTime;


	public static void step()
	{
		lastTime = System.currentTimeMillis();
	}


	public static float deltaTime()
	{
		return (System.currentTimeMillis() - lastTime) / 1000f;
	}

}
