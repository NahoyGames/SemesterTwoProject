package Launcher;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Launcher
{
	public static final String LAUNCHER_METADATA_LINK = "https://raw.githubusercontent.com/NahoyGames/SemesterTwoProject/master/launcherMeta";


	public static void main(String[] args)
	{
		String[] launcherMetaData;

		try
		{
			launcherMetaData = getLauncherMetaData();
			System.out.println("The latest game version is: " + launcherMetaData[0]);

		}
		catch (Exception e)
		{
			System.err.println("An error occurred while trying to fetch the latest game version...");
			System.exit(-1);
		}
	}


	private static String[] getLauncherMetaData() throws Exception
	{
		URL url = new URL(LAUNCHER_METADATA_LINK);

		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String version = in.readLine();
		String jarLink = in.readLine();

		in.close();

		return new String[] { version, jarLink };
	}

}
