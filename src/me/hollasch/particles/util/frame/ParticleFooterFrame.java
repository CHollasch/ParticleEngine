package me.hollasch.particles.util.frame;

import me.hollasch.particles.ParticleMain;
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
        setLayout(new GridLayout(1, 3));

        JButton debug = new JButton("Toggle Debug Mode");
        debug.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                host.toggleDebugMode();
            }
        });

        JButton visibility = new JButton("Toggle Options Visibility");
        visibility.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ParticleMain.OPTIONS_PANEL.setVisible(!ParticleMain.OPTIONS_PANEL.isVisible());
            }
        });

        add(new JLabel("Particle System v" + ParticleMain.VERSION_ID + ", By: Connor H. & Jason S."), 0, 0);
        add(debug, 0, 1);
        add(visibility, 0, 2);
    }
}
