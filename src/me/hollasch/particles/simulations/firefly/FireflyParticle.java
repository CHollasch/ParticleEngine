package me.hollasch.particles.simulations.firefly;

import me.hollasch.particles.options.NumberSliderOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.util.ColorUtil;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 2/8/2015
 */
public class FireflyParticle extends Particle {

    private static double JIGGLE = 0.00005;
    private static int SIZE = 1;

    protected static NumberSliderOption FIREFLY_JIGGLE = new NumberSliderOption(1, 100, 40, "Jiggle", new UpdateEvent<Integer>() {
        public void onUpdate(Integer option) {
            JIGGLE = (option.doubleValue() / 100000.0);
        }
    });

    protected static NumberSliderOption FIREFLY_SIZE = new NumberSliderOption(1, 10, 2, "Size", new UpdateEvent<Integer>() {
        @Override
        public void onUpdate(Integer option) {
            SIZE = option.intValue();
        }
    });

    private FireflyPath path;
    private double angle = Math.random() * 1000;

    public FireflyParticle(int x, int y) {
        super(x, y);

        path = new FireflyPath(Math.random() * 4 + Math.random() * 6, Math.random() * 4 + Math.random() * 6, true);
    }

    private boolean down = false;
    private double modifier = 0;

    private double color = 150;
    private Color firefly_color = ColorUtil.fromRGB(150, 150, 0);

    public void tick() {
        angle+=JIGGLE;
        double[] move = path.rotate(angle);

        centerX += move[0];
        centerY += move[1];

        if (down) {
            modifier-=0.01;
            if (modifier <= -2)
                down = false;
        } else {
            modifier+=0.01;
            if (modifier >= 2)
                down = true;
        }

        color+=modifier;

        firefly_color = modifyYellow((int)color);
    }

    public void paint(ParticleSystem particleHost, Graphics g) {
        g.setColor(firefly_color);
        g.fillOval(getCenterX(), getCenterY(), SIZE + (int)(5 * (1+modifier)), SIZE + (int)(5 * (1+modifier)));
    }

    //============

    private static Color modifyYellow(int modifier) {
        modifier = (modifier >= 255 ? 255 : modifier);
        float[] hsb = Color.RGBtoHSB(modifier, modifier, 0, new float[3]);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
}
