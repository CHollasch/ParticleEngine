package me.hollasch.particles.simulations.stars;

import me.hollasch.particles.options.NumberSliderOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.respawn.Respawnable;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class StarSpawnController extends Respawnable {

    public StarSpawnController() {
        addOption(new NumberSliderOption(1, 200, 25, "Spawn Range", new UpdateEvent<Integer>() {
            @Override
            public void onUpdate(Integer option) {
                spawnRadius = option;
            }
        }));
    }

    private int spawnRadius = 25;

    public void run() {
        int max = spawnRadius;
        int min = -spawnRadius;

        host.addParticle(new StarParticle(host.getWidth()/2, host.getHeight()/2));
    }

    public String getName() {
        return "Stars";
    }
}
