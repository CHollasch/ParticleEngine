package me.hollasch.particles.api;

import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.simulations.firefly.FireflySpawnController;
import me.hollasch.particles.simulations.firework.FireworkSpawnController;
import me.hollasch.particles.simulations.snow.SnowSpawnController;
import me.hollasch.particles.simulations.stars.StarSpawnController;

import java.util.HashSet;
import java.util.Set;

public class ParticleRespawnableQueue {

    private static ParticleSystem host;
    private static Set<Respawnable> respawnables = new HashSet<Respawnable>();

    public static final Respawnable SNOW_SPAWN_CONTROLLER = new SnowSpawnController().setFrequency(10);
    public static final Respawnable FIREWORK_SPAWN_CONTROLLER = new FireworkSpawnController().setFrequency(10);
    public static final Respawnable STAR_SPAWN_CONTROLLER = new StarSpawnController().setFrequency(10);
    public static final Respawnable FIREFLY_SPAWN_CONTROLLER = new FireflySpawnController().setFrequency(10);

    static {
        queueForCreation(SNOW_SPAWN_CONTROLLER);
        queueForCreation(FIREWORK_SPAWN_CONTROLLER);
        queueForCreation(STAR_SPAWN_CONTROLLER);
        queueForCreation(FIREFLY_SPAWN_CONTROLLER);
    }

    public static void link(ParticleSystem host) {
        ParticleRespawnableQueue.host = host;
    }

    public static Respawnable queueForCreation(Respawnable respawnable) {
        respawnables.add(respawnable);
        return respawnable;
    }

    public static Respawnable queuedForDestruction(Respawnable respawnable) {
        respawnables.remove(respawnable);
        return respawnable;
    }

    public static void subscribe() {
        for (Respawnable respawnable : respawnables) {
            respawnable.setHost(host);
            host.addRespawnTask(respawnable);
        }

        respawnables.clear();
    }
}
