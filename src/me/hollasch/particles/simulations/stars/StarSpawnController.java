package me.hollasch.particles.simulations.stars;

import me.hollasch.particles.respawn.Respawnable;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class StarSpawnController extends Respawnable {

    public void run() {
        host.addParticle(new StarParticle(host.getWidth()/2, host.getHeight()/2));
    }

    public String getName() {
        return "Stars";
    }
}
