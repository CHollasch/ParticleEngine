package me.hollasch.particles.firework;

import me.hollasch.particles.Respawnable;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class FireworkRespawnTask extends Respawnable {

    public void run() {
        host.addParticle(new FireworkBody((int) (Math.random() * host.getWidth()), host.getHeight()));
    }
}
