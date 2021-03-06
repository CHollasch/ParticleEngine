package me.hollasch.particles.simulations.firework.trails;

import me.hollasch.particles.options.declared.NumberRangedOption;
import me.hollasch.particles.options.declared.NumberSliderOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.simulations.firework.FireworkBody;
import me.hollasch.particles.util.ColorUtil;
import me.hollasch.particles.util.Range;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class FireworkSpark extends Particle {

    //STATIC OPTIONS
    private static Range speedRange;
    private static int ballSize = 3;

    public static NumberRangedOption SPEED_OPTION = new NumberRangedOption(10, 150, 20, 50, "Firework Size", new UpdateEvent<Range>() {
        public void onUpdate(Range option) {
            speedRange =  option;
        }
    });

    public static NumberSliderOption BALL_SIZE_OPTION = new NumberSliderOption(1, 10, 3, "Spark Size", new UpdateEvent<Integer>() {
        public void onUpdate(Integer option) {
            ballSize = option;
        }
    });

    protected Color color;

    protected int lastX;
    protected int lastY;

    protected int lifetime = (int) (Math.random() * 300 + 500);
    protected int initialLifespan = lifetime;

    protected double speedX;
    protected double speedY;

    public FireworkSpark(int x, int y, Particle parent, double angle, Color color) {
        super(x, y);
        this.color = color;

        centerX = parent.getCenterX();
        centerY = parent.getCenterY();

        this.parent = parent;

        lastX = getCenterX();
        lastY = getCenterY();
        double speed = speedRange.randomDouble();
        FireworkBody par = (FireworkBody)parent;
        speedX = speed * Math.cos(angle) - par.getSpeedX();
        speedY = speed * Math.sin(angle) + par.getSpeedY();
    }

    public FireworkSpark(int x, int y, Particle parent, double angle, Color color, int lifetime) {
        this(x, y, parent, angle, color);

        centerX = x;
        centerY = y;

        lastX = getCenterX();
        lastY = getCenterY();

        this.lifetime = lifetime;
    }

    public void tick() {
        if (lifetime-- < 0) {
            dead = true;
            return;
        }

        if (lifetime < (initialLifespan << 1)) {
            color = ColorUtil.modifyBrightness(color, .999f);
        }

        lastX = getCenterX();
        lastY = getCenterY();

        centerX -= speedX * .0166;
        centerY -= speedY * .0166 - 0.01358685;
        speedX = speedX * .993;
        speedY = speedY * .993;
        speedY  = speedY - 4.905 * .0166 ;

    }

    protected int sparkle = (int) (initialLifespan / (Math.random() + 1));

    public void paint(ParticleSystem particleHost, Graphics g) {
        g.setColor(color);
        if (lifetime < sparkle)
            g.fillOval(getCenterX(), getCenterY(), (int) (Math.random() * 2 + 1), (int) (Math.random() * 2 + 1));
        else
            g.fillOval(getCenterX(), getCenterY(), ballSize, ballSize);
    }
}
