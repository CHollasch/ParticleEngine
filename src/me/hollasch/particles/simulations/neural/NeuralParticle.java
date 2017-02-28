package me.hollasch.particles.simulations.neural;

import me.hollasch.particles.api.ParticleAPI;
import me.hollasch.particles.api.ParticleEngine;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.options.declared.NumberSliderOption;
import me.hollasch.particles.options.declared.ToggleOption;
import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since Feb 27, 4:57 PM
 */
public class NeuralParticle extends Particle {

    static int SIZE;
    static double SPEED;
    static int DISTANCE_REQUIRED;
    static int FALLOFF;
    static boolean MOUSE;

    static NumberSliderOption NEURAL_SIZE = new NumberSliderOption(2, 30, 5, "Neural Center Size", new UpdateEvent<Integer>() {
        @Override
        public void onUpdate(Integer option) {
            SIZE = option;
        }
    });

    static NumberSliderOption NEURAL_SPEED = new NumberSliderOption(1, 20, 8, "Neural Speed", new UpdateEvent<Integer>() {
        @Override
        public void onUpdate(Integer option) {
            SPEED = option / 10.0;
        }
    });

    static NumberSliderOption DISTANCE_SENSITIVITY = new NumberSliderOption(5, 500, 200, "Distance Sensitivity", new UpdateEvent<Integer>() {
        @Override
        public void onUpdate(Integer option) {
            DISTANCE_REQUIRED = option;
        }
    });

    static NumberSliderOption FALLOFF_CORRECTION = new NumberSliderOption(1, 5, 4, "Falloff Correction", new UpdateEvent<Integer>() {
        @Override
        public void onUpdate(Integer option) {
            FALLOFF = option;
        }
    });

    static ToggleOption MOUSE_AFFECTS = new ToggleOption(true, "Mouse Creates Neural Effect", new UpdateEvent<Boolean>() {
        @Override
        public void onUpdate(Boolean option) {
            MOUSE = option;
        }
    });

    private ParticleSystem host;
    private float realX;
    private float realY;

    private float directionX;
    private float directionY;

    public NeuralParticle(int x, int y, float directionX, float directionY, ParticleSystem host) {
        super(0, 0);

        this.directionX = directionX;
        this.directionY = directionY;

        this.realX = x;
        this.realY = y;

        this.host = host;
    }

    @Override
    public void tick() {
        if (this.realX > this.host.getWidth() + DISTANCE_REQUIRED || this.realX < -DISTANCE_REQUIRED
                || this.realY > this.host.getHeight() + DISTANCE_REQUIRED || this.realY < -DISTANCE_REQUIRED) {
            this.centerX = -1;
            this.centerY = -1;
            return;
        }

        this.realX += (this.directionX * SPEED);
        this.realY += (this.directionY * SPEED);
    }

    @Override
    public void paint(ParticleSystem particleHost, Graphics g) {
        for (Particle other : particleHost.getAlive()) {
            if (other instanceof NeuralParticle && !other.equals(this)) {
                drawIfApplicable(((NeuralParticle) other).realX, ((NeuralParticle) other).realY, Color.white, g);
            }
        }

        if (MOUSE) {
            drawIfApplicable((int) ParticleAPI.getMouseLocation().getX(), (int) ParticleAPI.getMouseLocation().getY(), Color.red, g);
        }

        g.setColor(Color.white);
        g.fillOval((int) this.realX - (SIZE / 2), (int) this.realY - (SIZE / 2), SIZE, SIZE);
    }

    private void drawIfApplicable(float ox, float oy, Color init, Graphics g) {
        int dx = (int) (ox - this.realX);
        int dy = (int) (oy - this.realY);

        double dist = Math.sqrt(dx*dx + dy*dy);
        double falloff = Math.pow(-(dist / DISTANCE_REQUIRED) + 1, 1.0 / FALLOFF);

        int red = (int) (init.getRed() * falloff);
        int green = (int) (init.getGreen() * falloff);
        int blue = (int) (init.getBlue() * falloff);

        if (dist < DISTANCE_REQUIRED) {
            g.setColor(new Color(red, green, blue));
            g.drawLine((int) ox, (int) oy, (int) this.realX, (int) this.realY);
        }
    }
}
