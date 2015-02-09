package me.hollasch.particles.simulations.stars;

import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class StarSpawnController extends Respawnable {

    public StarSpawnController() {
        respawnRateRange = new Range(1, 50);
        addOption(StarParticle.STAR_SIZE);
        addOption(StarParticle.STAR_SPREAD);
        addOption(StarParticle.STAR_ACCELERATION);
    }

    public void run() {
        for (int i = 0; i < 5; i++)
            host.addParticle(new StarParticle(host.getWidth()/2, host.getHeight()/2));
    }

    public String getName() {
        return "Stars";
    }
}
