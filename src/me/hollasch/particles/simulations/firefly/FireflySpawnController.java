package me.hollasch.particles.simulations.firefly;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since 2/8/2015
 */
public class FireflySpawnController extends Respawnable {

    public FireflySpawnController() {
        super(new Range(1, 30));

        addGUIOption(FireflyParticle.FIREFLY_SIZE);
        addGUIOption(FireflyParticle.FIREFLY_JIGGLE);
    }

    @Override
    public Particle nextParticle() {
        return new FireflyParticle((int) (Math.random() * host.getWidth()), (int) (Math.random() * host.getHeight()));
    }

    public String getName() {
        return "Firefly";
    }
}
