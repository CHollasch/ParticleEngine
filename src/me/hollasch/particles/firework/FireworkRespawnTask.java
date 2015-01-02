package me.hollasch.particles.firework;

import me.hollasch.particles.firework.trails.FireworkSpark;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.options.NumberRangedOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class FireworkRespawnTask extends Respawnable {

    public FireworkRespawnTask() {
        addOption(new NumberRangedOption(10, 500, 100, 150, "Spark Count", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                childRange = option;
            }
        }));

        addOption(new NumberRangedOption(10, 150, 30, 60, "Speed", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                speed = option;
            }
        }));

        //SPECIFIC PARTICLE OPTIONS

        addOption(FireworkSpark.SPEED_OPTION);
    }

    private Range childRange = new Range(100, 150);
    private Range speed = new Range(30, 60);

    public void run() {
        host.addParticle(new FireworkBody((int) (Math.random() * host.getWidth()),
                host.getHeight(),
                ((.392 * Math.random() - .196)  + 1.57075 ),
                speed.randomDouble(),
                childRange,
                ParticleSystem.colors[(int)(Math.random()*ParticleSystem.colors.length)]));
    }
}
