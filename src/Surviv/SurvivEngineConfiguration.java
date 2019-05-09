package Surviv;

import Surviv.Entities.Client.Player;
import Surviv.Networking.Packets.ClientLookAtPacket;
import Surviv.Networking.Packets.TestRequestPacket;
import Util.Engine.EngineConfiguration;


public class SurvivEngineConfiguration extends EngineConfiguration
{
	public SurvivEngineConfiguration()
	{
		super();

		/** REGISTER PACKETS >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_PACKETS.add(TestRequestPacket.class);
		REGISTERED_PACKETS.add(ClientLookAtPacket.class);


		/** REGISTER ENTITIES >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_ENTITIES.add(Player.class);
	}
}
