package me.hollasch.particles.util;

import me.hollasch.particles.ParticleSystem;
import me.hollasch.particles.Respawnable;
import me.hollasch.particles.options.NumberSliderOption;
import me.hollasch.particles.options.Source;
import me.hollasch.particles.options.UpdateEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        setLayout(new FlowLayout());
        add(speedSlider.get());
        add(respawnSlider.get());
        for (Respawnable spawn : host.getRespawnTasks()) {
            for (Source<?> option : spawn.getOptions()) {
                add((Component) option.get());
            }
        }
        add(clear);
    }
}
