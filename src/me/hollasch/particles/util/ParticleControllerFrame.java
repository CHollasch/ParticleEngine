package me.hollasch.particles.util;

import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.options.NumberSliderOption;
import me.hollasch.particles.options.Source;
import me.hollasch.particles.options.UpdateEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class ParticleControllerFrame extends JPanel {

    public ParticleControllerFrame(final ParticleSystem host) {
        //================= FRAME UPDATE INTERVAL ==================

        NumberSliderOption speedSlider = new NumberSliderOption(1, 25, 5, "Update Interval", new UpdateEvent<Integer>() {
            public void onUpdate(Integer option) {
                host.updateTickRate(option.intValue());
            }
        });

        //================= RESPAWN FREQUENCY ==================

        final NumberSliderOption respawnSlider = new NumberSliderOption(5, 1000, 900, "Respawn Interval", new UpdateEvent<Integer>() {
            public void onUpdate(Integer option) {
                host.setRespawnFrequency(-option.intValue() + 995);
            }
        });

        //================= CLEAR SCREEN ==================

        JButton clear = new JButton("Clear Screen");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                host.clear();
            }
        });

        //======================================================

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel mainOptions = new JPanel();
        mainOptions.add(new JLabel("Particle System Options"));
        mainOptions.add(speedSlider.get());
        mainOptions.add(respawnSlider.get());
        mainOptions.add(clear);
        add(mainOptions);

        for (final Respawnable spawn : host.getRespawnTasks()) {
            JPanel particlePane = new JPanel();
            particlePane.add(new JLabel(spawn.getName() + " Options"));
            for (Source<?> option : spawn.getOptions()) {
                particlePane.add((Component) option.get());
            }

            final JToggleButton onOrOff = new JToggleButton("Toggle Off");
            onOrOff.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (onOrOff.getText().endsWith("Off")) {
                        //off
                        spawn.off();
                        onOrOff.setText("Toggle On");
                    } else {
                        //on
                        spawn.on();
                        onOrOff.setText("Toggle Off");
                    }
                }
            });
            particlePane.add(onOrOff);
            add(particlePane);
        }
    }
}
