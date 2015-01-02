package me.hollasch.particles.firework;

import me.hollasch.particles.Particle;
import me.hollasch.particles.ParticleSystem;
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

    private int lifetime = (int) (Math.random() * 800 + 900);

    private double speedX;
    private double speedY;

    private boolean twinkle;

    public FireworkSpark(int x, int y, Particle parent, double angle, Color color) {
        super(x, y);
        this.color = color;

        centerX = parent.getCenterX();
        centerY = parent.getCenterY();

        this.parent = parent;

        lastX = getCenterX();
        lastY = getCenterY();
        double speed = 64 * Math.random() + .3;
        FireworkBody body = (FireworkBody) parent;
        speedX = speed * Math.cos(angle);
        speedY = speed * Math.sin(angle);
    }

    public void setTwinkle(boolean twinkle) {
        this.twinkle = twinkle;
    }

    public FireworkSpark(int x, int y, Particle parent, double angle, Color color, boolean forceTwinkle, int lifetime) {
        this(x, y, parent, angle, color);

        centerX = x;
        centerY = y;

        lastX = getCenterX();
        lastY = getCenterY();

        this.lifetime = lifetime;
        this.twinkle = forceTwinkle;
    }

    public void tick() {
        if (lifetime-- < 0) {
            dead = true;
            return;
        }

        lastX = getCenterX();
        lastY = getCenterY();

        centerX -= speedX / 60;
        centerY -= speedY / 60 - 4.905  * Math.pow(1 / 60,2);
        speedX = speedX * .993;
        speedY = speedY * .993;
        speedY  = speedY - 4.905 / 60 ;

    }

    public void paint(ParticleSystem particleHost, Graphics g) {
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
