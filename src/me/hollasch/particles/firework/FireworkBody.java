package me.hollasch.particles.firework;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.firework.trails.CrackleFireworkSpark;
import me.hollasch.particles.firework.trails.FireworkSpark;
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

        speedY -= 4.905 / 60;

        centerX += speedX / 60;
        centerY -= speedY / 60 + -4.905 / 360;
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        if (dead) {
            //explode
            for (int i = 0; i < children.randomInt(); i++) {
                FireworkSpark spark = new CrackleFireworkSpark(0, 0, this, (Math.random() * (2 * Math.PI)), (color == null ? ParticleSystem.colors[(int)(Math.random()*ParticleSystem.colors.length-1)] : color));

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
