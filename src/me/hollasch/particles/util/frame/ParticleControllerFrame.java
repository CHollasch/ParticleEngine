package me.hollasch.particles.util.frame;

import me.hollasch.particles.options.NumberRangedOption;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.respawn.Respawnable;
import me.hollasch.particles.options.NumberSliderOption;
import me.hollasch.particles.options.Source;
import me.hollasch.particles.options.UpdateEvent;
import me.hollasch.particles.util.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class ParticleControllerFrame extends JPanel {

    private static HashMap<String, JPanel> optionPanes = new HashMap<String, JPanel>();

    public ParticleControllerFrame(final ParticleSystem host) {

        //================= FRAME UPDATE INTERVAL ==================

        NumberSliderOption speedSlider = new NumberSliderOption(1, 31, 5, "Update Interval", new UpdateEvent<Integer>() {
            public void onUpdate(Integer option) {
                host.updateTickRate(option.intValue());
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
        mainOptions.setLayout(new WrapLayout());
        mainOptions.add(new JLabel("Particle System Options"));
        mainOptions.add(speedSlider.get());
        mainOptions.add(clear);
        add(mainOptions);

        JPanel spawnables = new JPanel();
        spawnables.setLayout(new WrapLayout());
        for (final Respawnable spawn : host.getRespawnTasks()) {
            final JButton toggle = new JButton("Enable " + spawn.getName());
            toggle.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (optionPanes.get(spawn.getName()).isVisible()) {
                        //hide
                        toggle.setText("Enable " + spawn.getName());
                        optionPanes.get(spawn.getName()).setVisible(false);
                        spawn.off();
                    } else {
                        //show
                        toggle.setText("Disable " + spawn.getName());
                        optionPanes.get(spawn.getName()).setVisible(true);
                        optionPanes.get(spawn.getName()).updateUI();
                        spawn.on();
                    }
                }
            });
            spawnables.add(toggle);
        }

        add(spawnables);

        for (final Respawnable spawn : host.getRespawnTasks()) {
            JPanel particlePane = new JPanel();
            particlePane.setLayout(new WrapLayout());

            particlePane.add(new JLabel(spawn.getName() + " Options"));

            final int min = (int) spawn.getRespawnRateRange().getMinimum();
            final int max = (int) spawn.getRespawnRateRange().getMaximum();

            particlePane.add(new NumberSliderOption(min, max-1, ((max - min) / 2), "Respawn Interval", new UpdateEvent<Integer>() {
                public void onUpdate(Integer option) {
                    int freq = (-option + (max - min));
                    freq = (freq < 1 ? 1 : freq);

                    host.setRespawnFrequency(spawn, freq);
                }
            }).get());

            for (Source<?> option : spawn.getOptions()) {
                particlePane.add((Component) option.get());
            }

            optionPanes.put(spawn.getName(), particlePane);
            particlePane.setVisible(false);
            spawn.off();
            add(particlePane);

            //CREATE TOGGLE BUTTON
        }

        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                for (JPanel p : optionPanes.values()) {
                    p.updateUI();
                }
            }

            public void componentMoved(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {}
            public void componentHidden(ComponentEvent e) {}
        });
    }
}
