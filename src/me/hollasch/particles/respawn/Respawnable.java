package me.hollasch.particles.respawn;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.options.Source;

import java.lang.reflect.Field;
import java.util.HashSet;

/**
 * Created by Connor on 12/31/2014.
 */
public abstract class Respawnable {

    protected ParticleSystem host;
    public long tick;
    private int frequency;

    private HashSet<Source<?>> options = new HashSet<Source<?>>();

    public abstract void run();

    public boolean spawn() {
        return (tick % frequency == 0);
    }

    public Respawnable setFrequency(int frequency) {
        this.frequency = frequency;
        return this;
    }

    public Respawnable setHost(ParticleSystem host) {
        this.host = host;
        return this;
    }

    protected void addOption(Source<?> option) {
        this.options.add(option);
    }

    public HashSet<Source<?>> getOptions() {
        return options;
    }
}