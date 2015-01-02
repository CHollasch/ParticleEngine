package me.hollasch.particles.firework;

import me.hollasch.particles.Particle;
import me.hollasch.particles.ParticleSystem;
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

    public FireworkBody(int x, int y) {
        this(x, y, ((.392 * Math.random() - .196)  + 1.57075 ), 30 *Math.random()+60, new Range(100, 150),  ParticleSystem.colors[(int)(Math.random()*ParticleSystem.colors.length)]);
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
            boolean twinkle = (Math.random() < .05);
            for (int i = 0; i < children.randomInt(); i++) {
                FireworkSpark spark = new FireworkSpark(0, 0, this, (Math.random() * (2 * Math.PI)), (color == null ? ParticleSystem.colors[(int)(Math.random()*ParticleSystem.colors.length-1)] : color));
                spark.setTwinkle(twinkle);

                particleHost.addParticle(spark);
            }
            return;
        }

        g.drawLine(lastX, lastY, getCenterX(), getCenterY());
        g.fillOval(getCenterX(), getCenterY(), 3, 3);
    }
}
