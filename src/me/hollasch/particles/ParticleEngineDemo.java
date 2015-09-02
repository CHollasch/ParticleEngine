package me.hollasch.particles;

import me.hollasch.particles.api.ParticleEngine;
import me.hollasch.particles.simulations.demo.DemoSpawnController;

public class ParticleEngineDemo {

    public static void main(String[] args) {
        ParticleEngine engine = new ParticleEngine();
        engine.addRespawnable(new DemoSpawnController().setFrequency(10));
        engine.startEngine();
    }
}
