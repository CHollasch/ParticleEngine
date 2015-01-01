package me.hollasch.particles;

/**
 * Created by Connor on 12/31/2014.
 */
public abstract class Respawnable {

    protected ParticleHost host;
    protected long tick;
    private int frequency;

    public abstract void run();

    public boolean spawn() {
        return (tick % frequency == 0);
    }

    public Respawnable setFrequency(int frequency) {
        this.frequency = frequency;
        return this;
    }

    public Respawnable setHost(ParticleHost host) {
        this.host = host;
        return this;
    }
}
