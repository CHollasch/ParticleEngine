package me.hollasch.particles.simulations.snow;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Connor on 12/31/2014.
 */
public class SnowParticle extends Particle {

    private int size;
    private double direction;

    private double fallSpeed = Math.random() * .1 + .4;
    private double speed = Math.random() * 1.5 + .5;
    private double degs = Math.random() * 360;

    public SnowParticle(int x, int y, double direction, int size) {
        super(x, y);
        this.direction = direction;
        this.size = size;
    }

    public void tick() {
        degs += speed/(-size + 15);
        degs = (degs >= 360 ? 0 : degs);
        centerY += (fallSpeed * Math.max(size / 3, 1));
        centerX += (direction + SimpleTrigLookup.SIN_VALUES[((int) (degs / 5))] * .15);
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        g.fillOval(getCenterX(), getCenterY(), size, size);
    }
}
