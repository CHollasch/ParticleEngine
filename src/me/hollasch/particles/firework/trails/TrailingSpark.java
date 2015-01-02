package me.hollasch.particles.firework.trails;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.util.ColorUtil;

import java.awt.*;
import java.util.*;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class TrailingSpark extends FireworkSpark {

    private Color trail;
    private java.util.List<Integer> coords = new ArrayList<Integer>();

    public TrailingSpark(int x, int y, Particle parent, double angle, Color color) {
        super(x, y, parent, angle, color);

        float[] colorhsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
        trail = Color.getHSBColor(colorhsb[0], colorhsb[1] / 2.5f, colorhsb[2]);
    }

    @Override
    public void tick() {
        super.tick();

        if (lifetime < (initialLifespan / 2)) {
            trail = ColorUtil.modifyBrightness(trail, .999f);
        }

        coords.add(lastX);
        coords.add(lastY);
    }

    @Override
    public void paint(ParticleSystem particleHost, Graphics g) {
        super.paint(particleHost, g);

        g.setColor(trail);
        for(int i = 0; i < coords.size() / 2 - 1; i += 2) {
            g.drawLine(coords.get(i), coords.get(i + 1), coords.get(i + 2), coords.get(i + 3));
        }
    }
}
