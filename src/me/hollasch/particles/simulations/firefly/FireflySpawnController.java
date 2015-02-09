package me.hollasch.particles.simulations.firefly;

import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since 2/8/2015
 */
public class FireflySpawnController extends Respawnable {

    public FireflySpawnController() {
        respawnRateRange = new Range(1, 30);

        addOption(FireflyParticle.FIREFLY_SIZE);
        addOption(FireflyParticle.FIREFLY_JIGGLE);
    }

    public void run() {
        host.addParticle(new FireflyParticle((int) (Math.random() * host.getWidth()), (int) (Math.random() * host.getHeight())));
    }

    public String getName() {
        return "Firefly";
    }
}
