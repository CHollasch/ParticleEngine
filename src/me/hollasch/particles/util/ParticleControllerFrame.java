package me.hollasch.particles.util;

import me.hollasch.particles.ParticleSystem;
import me.hollasch.particles.options.IntSliderOption;
import me.hollasch.particles.options.UpdateEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class ParticleControllerFrame extends JPanel {

    public ParticleControllerFrame(final ParticleSystem host) {
        //================= FRAME UPDATE INTERVAL ==================

        IntSliderOption speedSlider = new IntSliderOption(1, 25, 5, "Update Interval", new UpdateEvent<Integer>() {
            public void onUpdate(Integer option) {
                host.updateTickRate(option);
            }
        });

        //================= RESPAWN FREQUENCY ==================

        final IntSliderOption respawnSlider = new IntSliderOption(5, 1000, 900, "Respawn Interval", new UpdateEvent<Integer>() {
            public void onUpdate(Integer option) {
                host.setRespawnFrequency(-option + 995);
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
        add(clear);
    }
}
