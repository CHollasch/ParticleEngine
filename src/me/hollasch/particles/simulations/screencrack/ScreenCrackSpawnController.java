package me.hollasch.particles.simulations.screencrack;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.util.Range;

import java.util.HashSet;

/**
 * @author Connor Hollasch
 * @since 1/2/2015
 */
public class ScreenCrackSpawnController extends Respawnable {

    public ScreenCrackSpawnController() {
        respawnRateRange = new Range(5, 100);
        addOption(ScreenCrackParticle.CHILD_PERCENTAGE);
        addOption(ScreenCrackParticle.LIFETIME_OPTION);
    }

    private static HashSet<Particle> queue = new HashSet<Particle>();

    public void run() {
        host.addParticle(new ScreenCrackParticle((int) (Math.random() * host.getWidth()), (int) (Math.random() * host.getHeight())));

        for (Particle q : queue) {
            host.addParticle(q);
        }
        queue.clear();
    }

    public String getName() {
        return "Screen Crack";
    }

    public static void forge(ScreenCrackParticle screenCrackParticle) {
        queue.add(screenCrackParticle);
    }
}
