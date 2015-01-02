package me.hollasch.particles.simulations.firework;

import me.hollasch.particles.simulations.firework.trails.TrailingSpark;
import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.simulations.firework.trails.FireworkSpark;
import me.hollasch.particles.util.ColorUtil;
import me.hollasch.particles.util.Range;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class FireworkBody extends Particle {

    protected Color color;

    protected double speedY;
    protected double speedX;

    private Range children;

    private int lastX;
    private int lastY;

    public FireworkBody(int x, int y, double direction, double speed, Range children, Color color) {
        super(x, y);
        this.speedX = speed * Math.cos(direction);
        this.speedY = speed * Math.sin(direction);
        this.children = children;
        this.color = color;

        this.lastX = x;
        this.lastY = y;
    }

    public void tick() {
        lastX = getCenterX();
        lastY = getCenterY();

        if (speedY < 20) {
            dead = true;
            return;
        }

        speedY -= 4.905 * 0.016;

        centerX += speedX * 0.016;
        centerY -= speedY * 0.016 + -4.9 * 0.002;
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        if (dead) {
            //explode
            for (int i = 0; i < children.randomInt(); i++) {
                FireworkSpark spark = new TrailingSpark(0, 0, this, (Math.random() * (2 * Math.PI)), (color == null ? ColorUtil.colors[(int)(Math.random()*ColorUtil.colors.length-1)] : color));

                particleHost.addParticle(spark);
            }
            return;
        }

        g.drawLine(lastX, lastY, getCenterX(), getCenterY());
        g.fillOval(getCenterX(), getCenterY(), 3, 3);
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }
}
