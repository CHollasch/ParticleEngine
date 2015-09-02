package me.hollasch.particles.particle;

import me.hollasch.particles.respawn.Respawnable;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Connor on 12/31/2014.
 */
public abstract class Particle {

    private Respawnable respawnable;
    private boolean firstBorn = false;

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

    public void setRespawnable(Respawnable respawnable) {
        this.respawnable = respawnable;
    }

    public Respawnable getRespawnable() {
        return respawnable;
    }

    public void toggleFirstborn() {
        firstBorn = !firstBorn;
    }

    public boolean isFirstBorn() {
        return firstBorn;
    }

    public void kill() {
        dead = true;
    }
}
