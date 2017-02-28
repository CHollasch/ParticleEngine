package me.hollasch.particles.api;

import me.hollasch.particles.ParticleFrameHandler;
import me.hollasch.particles.respawn.Respawnable;

public class ParticleEngine {

    private String[] engine_arguments;

    public ParticleEngine(String... args) {
        this.engine_arguments = args;
    }

    public Respawnable addSpawnController(Respawnable respawnable) {
        return ParticleRespawnableQueue.queueForCreation(respawnable);
    }

    public void removeSpawnController(Respawnable respawnable) {
        ParticleRespawnableQueue.queuedForDestruction(respawnable);
    }

    public void startEngine() {
        ParticleFrameHandler.execute(engine_arguments);
    }
}
