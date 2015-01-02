package me.hollasch.particles.respawn;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.options.Source;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Connor on 12/31/2014.
 */
public abstract class Respawnable {

    protected ParticleSystem host;
    public long tick;
    private int frequency;

    private List<Source<?>> options = new ArrayList<Source<?>>();

    public abstract void run();

    public boolean spawn() {
        return (go && tick % frequency == 0);
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

    public List<Source<?>> getOptions() {
        return options;
    }

    public abstract String getName();

    private boolean go = true;

    public void off() {
        go = false;
    }

    public void on() {
        go = true;
    }
}
