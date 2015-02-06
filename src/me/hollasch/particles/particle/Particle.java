package me.hollasch.particles.particle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Connor on 12/31/2014.
 */
public abstract class Particle {

    protected double centerX;
    protected double centerY;

    protected boolean dead = false;

    protected Particle parent;

    public Particle(int x, int y) {
        this.centerX = x;
        this.centerY = y;
    }

    public void onMouseClick(MouseEvent event) {}

    public int getCenterX() {
        return (int) centerX;
    }

    public int getCenterY() {
        return (int) centerY;
    }

    public abstract void tick();

    public abstract void paint(ParticleSystem particleHost, Graphics g);
}
