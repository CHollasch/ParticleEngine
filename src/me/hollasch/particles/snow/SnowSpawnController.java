package me.hollasch.particles.snow;

import me.hollasch.particles.Respawnable;
import me.hollasch.particles.options.NumberRangedOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class SnowSpawnController extends Respawnable {

    public SnowSpawnController() {
        addOption(new NumberRangedOption(1, 10, 1, 3, "Snowflake Size", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                size = option;
            }
        }));
    }

    private Range size = new Range(1, 3);
    private Range direction = new Range(-.5, .5);

    public void run() {
        for (int i = 0; i < 5; i++)
            host.addParticle(new SnowParticle((int) (Math.random() * host.getWidth()), 0, direction.randomDouble(), size.randomInt()));
    }

    public SnowSpawnController setRandomSize(Range range) {
        this.size = range;
        return this;
    }

    public SnowSpawnController setRandomDirection(Range range) {
        this.direction = range;
        return this;
    }
}
