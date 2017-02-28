package me.hollasch.particles.simulations.firework;

import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.simulations.firework.trails.FireworkSpark;
import me.hollasch.particles.simulations.firework.trails.TrailingSpark;
import me.hollasch.particles.options.declared.NumberRangedOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.util.ColorUtil;
import me.hollasch.particles.util.Range;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class FireworkSpawnController extends Respawnable {

    public FireworkSpawnController() {
        super(new Range(10, 200));

        addGUIOption(new NumberRangedOption(10, 500, 100, 150, "Spark Count", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                childRange = option;
            }
        }));

        addGUIOption(new NumberRangedOption(10, 150, 60, 100, "Speed", new UpdateEvent<Range>() {
            public void onUpdate(Range option) {
                speed = option;
            }
        }));

        //SPECIFIC PARTICLE OPTIONS

        addGUIOption(FireworkSpark.SPEED_OPTION);
        addGUIOption(TrailingSpark.TRAIL_LENGTH_OPTION);
        addGUIOption(TrailingSpark.BALL_SIZE_OPTION);
    }

    private Range childRange = new Range(100, 150);
    private Range speed = new Range(30, 60);

    @Override
    public Particle nextParticle() {
        return new FireworkBody((int) (Math.random() * host.getWidth()),
                host.getHeight(),
                ((.392 * Math.random() - .196)  + 1.57075 ),
                speed.randomDouble(),
                childRange,
                ColorUtil.colors[(int)(Math.random()*ColorUtil.colors.length)]);
    }

    @Override
    public String getName() {
        return "Firework";
    }
}
