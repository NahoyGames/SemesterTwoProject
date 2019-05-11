package Launcher;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Launcher
{
	public static final String LAUNCHER_METADATA_LINK = "https://raw.githubusercontent.com/NahoyGames/SemesterTwoProject/master/src/Launcher/launcherMeta";
	public static final String LOCAL_VERSION_NAME = "yohanSemesterTwoProjectLatestRelease.jar";


	public static void main(String[] args)
	{
		String[] launcherMetaData;

		try
		{
			launcherMetaData = getLauncherMetaData();
			System.out.println("The latest game version is: " + launcherMetaData[0]);

			System.out.println("Attempting to download the latest game file at \"" + launcherMetaData[1] + "\"...");
			File localVersion = downloadGameVersion(launcherMetaData[1]);

			System.out.println("Successfully downloaded the latest game version!");

			System.out.println("Attempting to launch the game...");
			Runtime.getRuntime().exec("java -jar " + localVersion.getPath() + " " + launcherMetaData[2]);
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
		String serverIp = in.readLine();

		in.close();

		return new String[] { version, jarLink, serverIp };
	}


	private static File downloadGameVersion(String url) throws Exception
	{
		InputStream in = new URL(url).openStream();

		Files.copy(in, Paths.get(LOCAL_VERSION_NAME), StandardCopyOption.REPLACE_EXISTING);

		return Paths.get(LOCAL_VERSION_NAME).toFile();
	}

}
