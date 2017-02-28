package me.hollasch.particles.simulations.snow;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.options.declared.NumberRangedOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class SnowSpawnController extends Respawnable {

    public SnowSpawnController() {
        super(new Range(1, 50));

        addGUIOption(new NumberRangedOption(1, 15, 1, 3, "Snowflake Size", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                size = option;
            }
        }));

        addGUIOption(new NumberRangedOption(-10, 10, -5, 5, "Snowfall Direction", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                double min = option.getMinimum();
                double max = option.getMaximum();

                direction = new Range(min / 10d, max / 10d);
            }
        }));

        setAmplifiedSpawnRate(5);
    }

    private Range size = new Range(1, 3);
    private Range direction = new Range(-.5, .5);


    @Override
    public Particle nextParticle() {
        return new SnowParticle((int) (Math.random() * host.getWidth()), 0, direction.randomDouble(), size.randomInt());
    }

    @Override
    public String getName() {
        return "Snowflake";
    }
}
