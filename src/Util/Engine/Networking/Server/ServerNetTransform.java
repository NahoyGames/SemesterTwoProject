package Util.Engine.Networking.Server;

import Util.Engine.*;
import Util.Engine.Networking.Packets.TransformPacket;

public class ServerNetTransform extends GameBehavior
{
	private ServerGameEntity target;
	private final float syncRate; // Updates per second cap

	private Transform2D lastSentTransform;
	private float timeElapsed;


	public ServerNetTransform(ServerGameEntity target, int syncRate)
	{
		this.target = target;
		this.syncRate = 1 / syncRate;

		this.lastSentTransform = new Transform2D();
	}


	@Override
	public void update()
	{
		timeElapsed += Time.deltaTime();

		if (timeElapsed >= syncRate && (!target.transform().position.equals(lastSentTransform.position) || target.transform().rotation != lastSentTransform.rotation))
		{
			timeElapsed = 0;

			((ServerNetManager)Engine.netManager()).sendUnreliable(new TransformPacket(target));
			lastSentTransform.position.x = target.transform().position.x;
			lastSentTransform.position.y = target.transform().position.y;
			lastSentTransform.rotation = target.transform().rotation;
		}
	}
}
