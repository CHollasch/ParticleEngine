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

    private static Color[] colors = {Color.blue, Color.green, Color.magenta, Color.cyan, Color.orange, Color.pink, Color.red, Color.yellow, null};

    protected Color color;

    protected double direction;
    protected double speed;

    private Range children;

    private int lastX;
    private int lastY;

    public FireworkBody(int x, int y, double direction, double speed, Range children, Color color) {
        super(x, y);
        this.direction = direction;
        this.speed = speed;
        this.children = children;
        this.color = color;

        this.lastX = x;
        this.lastY = y;
    }

    public FireworkBody(int x, int y) {
        this(x, y, (Math.random()-.5), Math.random()+3, new Range(100, 150),  colors[(int)(Math.random()*colors.length)]);
    }

    public void tick() {
        lastX = getCenterX();
        lastY = getCenterY();

        if (speed < 0.1) {
            dead = true;
            return;
        }

        direction += (.0003 * direction);

        centerX += direction;
        centerY -= (speed-=0.01);
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        if (dead) {
            //explode
            boolean twinkle = (Math.random() < .05);
            for (int i = 0; i < children.randomInt(); i++) {
                FireworkSpark spark = new FireworkSpark(0, 0, this, (Math.random() * (2 * Math.PI)), (color == null ? colors[(int)(Math.random()*colors.length-1)] : color));
                spark.setTwinkle(twinkle);

                particleHost.addParticle(spark);
            }
            return;
        }

        g.drawLine(lastX, lastY, getCenterX(), getCenterY());
        g.fillOval(getCenterX(), getCenterY(), 3, 3);
    }
}
