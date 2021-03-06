package me.hollasch.particles.ui;

import me.hollasch.particles.ParticleFrameHandler;
import me.hollasch.particles.particle.ParticleSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class ParticleFooterFrame extends JPanel {

    public ParticleFooterFrame(final ParticleSystem host) {
        setLayout(new GridLayout(1, 4));

        JButton debug = new JButton("Toggle Debug Mode");
        debug.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                host.toggleDebugMode();
            }
        });

        JButton visibility = new JButton("Toggle Options Visibility");
        visibility.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ParticleFrameHandler.OPTIONS_PANEL.setVisible(!ParticleFrameHandler.OPTIONS_PANEL.isVisible());
            }
        });

        JButton fullscreen = new JButton("Toggle Fullscreen");
        fullscreen.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (ParticleFrameHandler.MAIN_FRAME.isUndecorated()) {
                    //fullscreen
                    ParticleFrameHandler.MAIN_FRAME.getRootPane().getActionMap().get("Cancel").actionPerformed(null);
                } else {
                    //not fullscreen
                    ParticleFrameHandler.MAIN_FRAME.getRootPane().getActionMap().get("Fullscreen").actionPerformed(null);
                }
            }
        });

        add(new JLabel("Connor Hollasch, Jason Stallkamp"), 0, 0);
        add(debug, 0, 1);
        add(visibility, 0, 2);
        add(fullscreen, 0, 3);
    }
}
