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

    private int lifetime = (int) (Math.random() * 300 + 500);
    private int initialLifespan = lifetime;

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
        FireworkBody par = (FireworkBody)parent;
        speedX = speed * Math.cos(angle) - par.speedX;
        speedY = speed * Math.sin(angle) + par.speedY;
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

        if (lifetime < (initialLifespan / 2)) {
            int red = (int) (color.getRed() * .999f);
            int green = (int) (color.getGreen() * .999f);
            int blue = (int) (color.getBlue() * .999f);

            float[] hsb = Color.RGBtoHSB(red, green, blue, new float[3]);
            color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        }

        lastX = getCenterX();
        lastY = getCenterY();

        centerX -= speedX * .0166;
        centerY -= speedY * .0166 - 4.905 * .00277;
        speedX = speedX * .993;
        speedY = speedY * .993;
        speedY  = speedY - 4.905 * .0166 ;

    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        if (twinkle && color.getRed() < 50 && color.getGreen() < 50 && color.getBlue() < 50) {
            for (int i = 0; i < new Range(10, 15).randomInt(); i++) {
                particleHost.addParticle(new FireworkSpark(getCenterX(), getCenterY(), parent, (Math.random() * Math.PI * 2), Color.white, false, (int) (Math.random() * 30 + 10)));
            }
            twinkle = false;
            return;
        }

        g.setColor(color);
        g.drawLine(lastX, lastY, getCenterX(), getCenterY());
    }
}
