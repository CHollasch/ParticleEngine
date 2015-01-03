package me.hollasch.particles.simulations.screencrack;

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
 * @since 1/2/2015
 */
public class ScreenCrackParticle extends Particle {

    public static NumberRangedOption LIFETIME_OPTION = new NumberRangedOption(100, 1500, 200, 800, "Crack Lifetime", new UpdateEvent<Range>() {
        public void onUpdate(Range option) {
            lifetime = option;
        }
    });

    public static NumberSliderOption CHILD_PERCENTAGE  = new NumberSliderOption(1, 25, 5, "Child Percentage", new UpdateEvent<Integer>() {
        public void onUpdate(Integer option) {
            child_perctnage = option;
        }
    });

    private static Range lifetime = new Range(200, 800);
    private static int child_perctnage;

    private Color color = Color.white;
    private int lifespan = lifetime.randomInt();
    private int initialLifetime = lifespan;

    private boolean makeChild = (child_perctnage >= Math.random() * 100);

    private double pathX;
    private double pathY;

    public ScreenCrackParticle(int x, int y) {
        super(x, y);

        double direction = Math.random() * (Math.PI * 2);

        pathX = x + Math.cos(direction) * (Math.random() * 100);
        pathY = y + Math.sin(direction) * (Math.random() * 100);
    }

    public void tick() {

        if (makeChild && lifespan < (initialLifetime / 3)) {
            ScreenCrackSpawnController.forge(new ScreenCrackParticle(getCenterX(), getCenterY()));
            makeChild = false;
        }
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        g.setColor(color);
        g.drawLine(getCenterX(), getCenterY(), (int) pathX, (int) pathY);
    }
}
