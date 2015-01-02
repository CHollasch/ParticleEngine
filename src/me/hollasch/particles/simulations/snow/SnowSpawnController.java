package me.hollasch.particles.simulations.snow;

import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.options.NumberRangedOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class SnowSpawnController extends Respawnable {

    public SnowSpawnController() {
        respawnRateRange = new Range(1, 50);

        addOption(new NumberRangedOption(1, 10, 1, 3, "Snowflake Size", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                size = option;
            }
        }));

        addOption(new NumberRangedOption(-10, 10, -5, 5, "Snowfall Direction", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                double min = option.getMinimum();
                double max = option.getMaximum();

                direction = new Range(min/10d, max/10d);
            }
        }));
    }

    private Range size = new Range(1, 3);
    private Range direction = new Range(-.5, .5);

    public void run() {
        for (int i = 0; i < 5; i++)
            host.addParticle(new SnowParticle((int) (Math.random() * host.getWidth()), 0, direction.randomDouble(), size.randomInt()));
    }

    @Override
    public String getName() {
        return "Snowflake";
    }
}
