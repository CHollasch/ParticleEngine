package me.hollasch.particles.simulations.firework.trails;

import me.hollasch.particles.options.declared.NumberRangedOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.util.ColorUtil;
import me.hollasch.particles.util.Range;

import java.awt.*;
import java.util.*;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class TrailingSpark extends FireworkSpark {

    //STATIC OPTIONS
    private static Range trailRange;

    public static NumberRangedOption TRAIL_LENGTH_OPTION = new NumberRangedOption(50, 200, 100, 150, "Trail Length", new UpdateEvent<Range>() {
        public void onUpdate(Range option) {
            trailRange = option;
        }
    });

    private int maxTrailLength;

    private Color trail;
    private java.util.List<Integer> xCoords = new ArrayList<Integer>();
    private java.util.List<Integer> yCoords = new ArrayList<Integer>();

    public TrailingSpark(int x, int y, Particle parent, double angle, Color color) {
        super(x, y, parent, angle, color);

        float[] colorhsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
        trail = ColorUtil.modifyBrightness(Color.getHSBColor(colorhsb[0], colorhsb[1] / 2.5f, colorhsb[2]), (float) (Math.random()/3 + .5));

        maxTrailLength = trailRange.randomInt();
    }

    public void tick() {
        super.tick();

        if (lifetime < (initialLifespan >> 1)) {
            trail = ColorUtil.modifyBrightness(trail, .999f);
        }

        if (xCoords.size() > maxTrailLength) {
            xCoords.remove(0);
            yCoords.remove(0);
        }

        xCoords.add(lastX);
        yCoords.add(lastY);
    }

    @Override
    public void paint(ParticleSystem particleHost, Graphics g) {
        super.paint(particleHost, g);

        g.setColor(trail);
        for(int i = 0; i < xCoords.size() / 2 - 1; i += 2) {
            g.drawLine(xCoords.get(i), yCoords.get(i), xCoords.get(i + 1), yCoords.get(i + 1));
        }
    }
}
