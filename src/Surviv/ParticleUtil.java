package Surviv;

import Util.Engine.Particles.ParticleSystem;
import Util.Engine.Particles.ParticleSystemConfig;
import Util.Engine.Scene;
import Util.Math.Vec2f;

public class ParticleUtil
{
	private static ParticleSystemConfig SMOKE_CONFIG = new ParticleSystemConfig(15, 0.5f, 5f, "/Assets/Sprites/Effects/smoke.png", 0, 360, 15);


	public static ParticleSystem spawnExplosionParticles(Scene scene, Vec2f pos, float scale)
	{
		return (ParticleSystem) scene.addEntity(new ParticleSystem(scene, SMOKE_CONFIG, pos, Vec2f.one().scale(scale)));
	}

}
