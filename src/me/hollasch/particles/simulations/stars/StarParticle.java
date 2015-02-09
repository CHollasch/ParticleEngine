package me.hollasch.particles.simulations.stars;

import me.hollasch.particles.options.NumberRangedOption;
import me.hollasch.particles.options.NumberSliderOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.util.ColorUtil;
import me.hollasch.particles.util.Range;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class StarParticle extends Particle {

    private static int SIZE;
    private static Range SPREAD = new Range(5, 30);
    private static double ACCELERATION = 0.02;

    protected static NumberSliderOption STAR_SIZE = new NumberSliderOption(2, 30, 5, "Star Size", new UpdateEvent<Integer>() {
        @Override
        public void onUpdate(Integer option) {
            SIZE = option;
        }
    });

    protected static NumberRangedOption STAR_SPREAD = new NumberRangedOption(1, 200, 5, 30, "Star Spread", new UpdateEvent<Range>() {
        @Override
        public void onUpdate(Range option) {
            SPREAD = option;
        }
    });

    protected static NumberSliderOption STAR_ACCELERATION = new NumberSliderOption(10, 1000, 200, "Star Acceleration", new UpdateEvent<Integer>() {
        @Override
        public void onUpdate(Integer option) {
            ACCELERATION = (option.doubleValue() / 10000.0) + 1;
        }
    });

    private Color star_color;

    private double size;

    private double speed = 0.1;

    private double cos;
    private double sin;

    private int brightness = 0;
    private double direction = Math.random() * (Math.PI * 2);

    public StarParticle(int x, int y) {
        super(x, y);

        star_color = Color.black;

        cos = Math.cos(direction);
        sin = Math.sin(direction);

        centerX += (SPREAD.randomInt() * cos);
        centerY += (SPREAD.randomInt() * sin);

        size = SIZE;
    }

    public void tick() {
        star_color = ColorUtil.fromRGB((++brightness >= 255 ? brightness=255 : brightness), brightness, brightness);
        speed *= ACCELERATION;

        centerX += (cos * speed);
        centerY += (sin * speed);

        size += 0.01;
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        g.setColor(star_color);
        g.fillOval(getCenterX(), getCenterY(), (int)size, (int)size);
    }
}
