package me.hollasch.particles;

import me.hollasch.particles.options.Source;

import java.util.HashSet;

/**
 * Created by Connor on 12/31/2014.
 */
public abstract class Respawnable {

    protected ParticleSystem host;
    protected long tick;
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

    public HashSet<Source<?>> getOptions() {
        return options;
    }
}
