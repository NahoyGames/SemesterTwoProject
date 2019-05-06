package Surviv;

import Surviv.Entities.Client.Player;
import Util.Engine.EngineConfiguration;
import Util.Engine.Networking.Packets.SpawnEntityPacket;


public class SurvivEngineConfiguration extends EngineConfiguration
{
	public SurvivEngineConfiguration()
	{
		super();

		/** REGISTER PACKETS >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_PACKETS.add(SpawnEntityPacket.class);


		/** REGISTER ENTITIES >>HERE<< FOR BOTH CLIENT & SERVER **/
		REGISTERED_ENTITIES.add(Player.class);
	}
}
