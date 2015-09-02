package me.hollasch.particles.simulations.stars;

import me.hollasch.particles.api.ParticleAPI;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.options.declared.DropdownOption;
import me.hollasch.particles.options.declared.ToggleOption;
import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.simulations.firefly.FireflyPath;
import me.hollasch.particles.util.Range;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class StarSpawnController extends Respawnable {

    private static int pathAction;

    public StarSpawnController() {
        super(new Range(1, 50));

        addOption(StarParticle.STAR_SIZE);
        addOption(StarParticle.STAR_SPREAD);
        addOption(StarParticle.STAR_ACCELERATION);

        addOption(new DropdownOption<>(new String[]{"At Center", "Follow Mouse", "Circle"}, "At Center", "Spawn Location", new UpdateEvent<String>() {
            @Override
            public void onUpdate(String option) {
                switch (option) {
                    case "At Center": pathAction = 0; return;
                    case "Follow Mouse": pathAction = 1; return;
                    default: pathAction = 2; return;
                }
            }
        }));

        setAmplifiedSpawnRate(20);
    }

    private double degrees;

    @Override
    public Particle nextParticle() {
        switch (pathAction) {
            case 0: {
                return new StarParticle(host.getWidth() / 2, host.getHeight() / 2);
            }
            case 1: {
                return new StarParticle((int) ParticleAPI.getMouseLocation().getX(), (int) ParticleAPI.getMouseLocation().getY());
            }
            default: {
                degrees += 0.1;

                double cos = Math.cos(Math.toRadians(degrees)) * StarParticle.STAR_SPREAD.get().getUpperValue();
                double sin = Math.sin(Math.toRadians(degrees))* StarParticle.STAR_SPREAD.get().getUpperValue();

                return new StarParticle((int) (ParticleAPI.getEngineWidth() / 2 + cos), (int) (ParticleAPI.getEngineHeight() / 2 + sin));
            }
        }
    }

    public String getName() {
        return "Stars";
    }
}
