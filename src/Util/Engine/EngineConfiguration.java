package Util.Engine;

import Util.Engine.Networking.Client.ClientGameEntity;
import Util.Engine.Networking.Packet;
import Util.Engine.Networking.Packets.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EngineConfiguration
{
	/** Window **/
	public String WINDOW_NAME = "My Engine";
	public int FRAMES_PER_SECOND = 60;
	public int WINDOW_WIDTH = 600;
	public int WINDOW_HEIGHT = 400;
	public boolean HEADLESS_MODE = false; // if (true) --> no graphics, no input

	/** Engine **/
	public String[] SUPPORTED_VERSIONS;
	public String VERSION;

	/** Scenes **/
	public HashMap<String, Scene> REGISTERED_SCENES;
	public String DEFAULT_SCENE;

	/** Networking **/
	public boolean IS_SERVER_BUILD = true;
	public int TCP_PORT = 54555;
	public int UDP_PORT = 54777;
	public String SERVER_IP = "127.0.0.1"; // Local host
	public Collection<Class<? extends Packet>> REGISTERED_PACKETS;
	public List<Class<? extends ClientGameEntity>> REGISTERED_ENTITIES;


	public EngineConfiguration()
	{
		REGISTERED_PACKETS = new ArrayList<>();
		REGISTERED_ENTITIES = new ArrayList<>();

		// Default, "built-in," Packets
		REGISTERED_PACKETS.add(SpawnEntityPacket.class);
		REGISTERED_PACKETS.add(ClientAuthResponsePacket.class);
		REGISTERED_PACKETS.add(ClientAuthRequestPacket.class);
		REGISTERED_PACKETS.add(GameStatePacket.class);
		REGISTERED_PACKETS.add(TransformPacket.class);
	}
}
