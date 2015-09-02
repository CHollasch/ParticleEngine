package me.hollasch.particles.simulations.demo;

import me.hollasch.particles.api.ParticleAPI;
import me.hollasch.particles.options.declared.NumberRangedOption;
import me.hollasch.particles.options.declared.NumberSliderOption;
import me.hollasch.particles.options.declared.ToggleOption;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.particle.Particle;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.simulations.snow.SnowParticle;
import me.hollasch.particles.simulations.stars.StarParticle;
import me.hollasch.particles.util.Range;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DemoSpawnController extends Respawnable {

    private static double changeSpeed;
    private static boolean changingSizes;
    private static boolean solid;
    private static Range size;

    public DemoSpawnController() {
        super(new Range(1, 10));

        setOneParticleAtATime(true);

        addOption(new NumberRangedOption(10, 1000, 10, 50, "Bubble Size", new UpdateEvent<Range>() {
            @Override
            public void onUpdate(Range option) {
                size = option;
            }
        }));
        addOption(new ToggleOption(false, "Solid Circle", new UpdateEvent<Boolean>() {
            public void onUpdate(Boolean option) {
                solid = option;
            }
        }));
        addOption(new ToggleOption(false, "Changing Sizes", new UpdateEvent<Boolean>() {
            public void onUpdate(Boolean option) {
                changingSizes = option;
            }
        }));
        addOption(new NumberSliderOption(1, 10, 5, "Change  Speed", new UpdateEvent<Integer>() {
            public void onUpdate(Integer option) {
                changeSpeed = option;
            }
        }));
    }

    @Override
    public Particle nextParticle() {
        return new Particle((int) (Math.random() * ParticleAPI.getEngineWidth()),
                (int) (Math.random() * ParticleAPI.getEngineHeight())) {

            boolean following = false;
            float hsv;
            boolean down = false;

            double size = 25;

            @Override
            public void onMouseClick(MouseEvent event) {
                following = !following;
            }

            @Override
            public void tick() {
                if (changingSizes) {
                    size += (down ? -(size * (.004 * changeSpeed)) : (size * (.004 * changeSpeed)));
                }

                if (size > DemoSpawnController.size.getMaximum()) {
                    down = true;
                } else if (size < DemoSpawnController.size.getMinimum()) {
                    down = false;
                }

                if (following) {
                    centerX = ParticleAPI.getMouseLocation().getX();
                    centerY = ParticleAPI.getMouseLocation().getY();
                }

                hsv = (hsv + 0.01f > 1 ? 0 : hsv + 0.01f);
            }

            @Override
            public void paint(ParticleSystem particleHost, Graphics g) {
                if (solid) {
                    for (int i = 0; i < size; ++i) {
                        float tempHsv = computeHSV(hsv, (float) (i * (0.5 / size)));

                        g.setColor(Color.getHSBColor(tempHsv, 1, 1));

                        g.fillOval((int) (getCenterX() + (i / 2.0d) - (size / 2)), (int) (getCenterY() + (i / 2.0d) - (size / 2)), (int) size - i, (int) size - i);
                    }
                } else {
                    g.setColor(Color.getHSBColor(hsv, 1, 1));

                    g.drawOval((int) (getCenterX() - (size / 2)), (int) (getCenterY() - (size / 2)), (int) size, (int) size);
                }
            }

            private float computeHSV(float original, float decrement) {
                float decrementVal = decrement % 1;

                if (original - decrement <= 0) {
                    return computeHSV(1, (decrement - original));
                }

                return original - decrementVal;
            }
        };
    }

    @Override
    public String getName() {
        return "Demo";
    }
}
