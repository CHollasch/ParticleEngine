package me.hollasch.particles.firework;

import me.hollasch.particles.Particle;
import me.hollasch.particles.ParticleHost;
import me.hollasch.particles.util.Range;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class FireworkSpark extends Particle {

    private Color color;

    private int lastX;
    private int lastY;

    private int lifetime = (int) (Math.random() * 200 + 100);

    private double speed;
    private double angle;

    private boolean twinkle;

    public FireworkSpark(int x, int y, Particle parent, double angle, Color color) {
        super(x, y);
        this.color = color;

        centerX = parent.getCenterX();
        centerY = parent.getCenterY();

        this.parent = parent;

        lastX = getCenterX();
        lastY = getCenterY();

        speed = Math.random() + .1;

        this.angle = angle;
    }

    public void setTwinkle(boolean twinkle) {
        this.twinkle = twinkle;
    }

    public FireworkSpark(int x, int y, Particle parent, double angle, Color color, boolean forceTwinkle, int lifetime) {
        super(x, y);
        this.color = color;

        this.parent = parent;

        lastX = getCenterX();
        lastY = getCenterY();

        speed = Math.random() + .1;

        this.lifetime = lifetime;
        this.angle = angle;
        this.twinkle = forceTwinkle;
    }

    public void tick() {
        if (lifetime-- < 0) {
            dead = true;
            return;
        }

        lastX = getCenterX();
        lastY = getCenterY();

        centerX -= speed * Math.cos(angle);
        centerY -= speed * Math.sin(angle) - 0.1;

        speed += (speed > 0 ? -0.0001 : speed+0.0001);
    }

    public void paint(ParticleHost particleHost, Graphics g) {
        if (dead) {
            if (twinkle) {
                for (int i = 0; i < new Range(10, 15).randomInt(); i++) {
                    particleHost.addParticle(new FireworkSpark(getCenterX(), getCenterY(), parent, (Math.random() * Math.PI * 2), Color.white, false, (int) (Math.random() * 30 + 10)));
                }
            }
            return;
        }

        if (lifetime < 10)
            g.setColor(Color.white);
        else
            g.setColor(color);

        g.drawLine(lastX, lastY, getCenterX(), getCenterY());
    }
}
