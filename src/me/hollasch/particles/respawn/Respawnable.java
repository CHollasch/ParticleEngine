package me.hollasch.particles.respawn;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.options.Source;
import me.hollasch.particles.util.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Connor on 12/31/2014.
 */
public abstract class Respawnable {

    protected ParticleSystem host;
    protected Range respawnRateRange;

    private boolean hasFirstborn = false;
    private boolean onlySpawnOne = false;

    private int amplifiedRate = -1;

    public long tick;
    private int frequency;

    private List<Source<?>> options = new ArrayList<Source<?>>();

    public Respawnable(Range spawnRate) {
        this.respawnRateRange = spawnRate;
    }

    protected void setAmplifiedSpawnRate(int amplificationValue) {
        this.amplifiedRate = amplificationValue;
    }

    protected void setOneParticleAtATime(boolean oneParticleAtATime) {
        this.onlySpawnOne = oneParticleAtATime;
    }

    public abstract Particle nextParticle();

    public void run() {
        if (hasFirstborn && onlySpawnOne) {
            return;
        }

        if (amplifiedRate != -1) {
            for (int num = 0; num < amplifiedRate; ++num) {
                host.addParticle(prep(nextParticle()));
            }
        } else {
            host.addParticle(prep(nextParticle()));
        }

        hasFirstborn = true;
    }

    private Particle prep(Particle in) {
        if (onlySpawnOne) {
            in.toggleFirstborn();
        }

        in.setRespawnable(this);

        return in;
    }

    public boolean canSpawn() {
        return (respawningParticles && tick % frequency == 0);
    }

    public Respawnable setFrequency(int frequency) {
        this.frequency = frequency;
        return this;
    }

    public Respawnable setHost(ParticleSystem host) {
        this.host = host;
        return this;
    }

    public void onParticleDied(Particle particle) {
        if (hasFirstborn && particle.isFirstBorn()) {
            hasFirstborn = false;
        }
    }

    protected void addOption(Source<?> option) {
        this.options.add(option);
    }

    public List<Source<?>> getOptions() {
        return options;
    }

    public abstract String getName();

    private boolean respawningParticles = false;

    public void disable() {
        respawningParticles = false;
    }

    public void enable() {
        respawningParticles = true;
    }

    public Range getRespawnRateRange() {
        return respawnRateRange;
    }
}
