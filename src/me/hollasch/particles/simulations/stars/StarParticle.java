package me.hollasch.particles.simulations.stars;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class StarParticle extends Particle {

    private double frontX;
    private double frontY;

    private double direction = Math.random() * (Math.PI * 2);

    public StarParticle(int x, int y) {
        super(x, y);
    }

    public void tick() {

    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        g.setColor(Color.white);
        g.drawLine(getCenterX(), getCenterY(), (int) frontX, (int) frontY);
    }
}
