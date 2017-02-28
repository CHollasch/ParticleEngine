package me.hollasch.particles.simulations.neural;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since Feb 27, 4:56 PM
 */
public class NeuralSpawnController extends Respawnable {

    public NeuralSpawnController() {
        super(new Range(1, 50));

        addGUIOption(NeuralParticle.NEURAL_SIZE);
        addGUIOption(NeuralParticle.NEURAL_SPEED);
        addGUIOption(NeuralParticle.DISTANCE_SENSITIVITY);
        addGUIOption(NeuralParticle.FALLOFF_CORRECTION);
        addGUIOption(NeuralParticle.MOUSE_AFFECTS);
    }

    @Override
    public Particle nextParticle() {
        int x, y;
        float dirX, dirY;

        if (Math.random() > .5) {
            y = (Math.random() > .5 ? 0 : 1) * host.getHeight();
            x = (int) (Math.random() * host.getWidth());
            dirX = (float) Math.random();

            if (y == 0) {
                dirY = 1;
            } else {
                dirY = -1;
            }

            y = (y == 0 ? -NeuralParticle.DISTANCE_REQUIRED : y + NeuralParticle.DISTANCE_REQUIRED);
        } else {
            y = (int) (Math.random() * host.getHeight());
            x = (Math.random() > .5 ? 0 : 1) * host.getWidth();
            dirY = (float) Math.random();

            if (x == 0) {
                dirX = 1;
            } else {
                dirX = -1;
            }

            x = (x == 0 ? -NeuralParticle.DISTANCE_REQUIRED : x + NeuralParticle.DISTANCE_REQUIRED);
        }

        return new NeuralParticle(x, y, dirX, dirY, host);
    }

    @Override
    public String getName() {
        return "Neural";
    }
}
