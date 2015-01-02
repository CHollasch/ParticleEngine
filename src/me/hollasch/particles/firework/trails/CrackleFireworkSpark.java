package me.hollasch.particles.firework.trails;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.util.Range;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class CrackleFireworkSpark extends FireworkSpark {

    private boolean twinkle = true;

    public CrackleFireworkSpark(int x, int y, Particle parent, double angle, Color color) {
        super(x, y, parent, angle, color);
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        super.paint(particleHost, g);

        if (twinkle && color.getRed() < 50 && color.getGreen() < 50 && color.getBlue() < 50) {
            for (int i = 0; i < new Range(10, 15).randomInt(); i++) {
                particleHost.addParticle(new FireworkSpark(getCenterX(), getCenterY(), parent, (Math.random() * Math.PI * 2), Color.white, (int) (Math.random() * 30 + 10)));
            }
            twinkle = false;
            return;
        }
    }
}
