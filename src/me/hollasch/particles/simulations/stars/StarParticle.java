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
    private double speed = 1.1;

    public StarParticle(int x, int y) {
        super(x, y);

        frontX = x + Math.cos(direction) * 30;
        frontY = y + Math.sin(direction) * 30;
    }

    public void tick() {

        double x = Math.cos(direction);
        double y = Math.sin(direction);

        speed*=1.01;

        centerX += (x * speed);
        centerY += (y * speed);

        frontX += (x * speed);
        frontY += (y * speed);
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        g.setColor(Color.white);
        g.drawLine(getCenterX(), getCenterY(), (int) frontX, (int) frontY);
    }
}
