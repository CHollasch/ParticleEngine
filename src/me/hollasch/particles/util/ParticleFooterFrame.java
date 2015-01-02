package me.hollasch.particles.util;

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

    public ParticleFooterFrame() {
        setLayout(new BorderLayout());

        JButton visibility = new JButton("Toggle Options Visibility");
        visibility.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ParticleMain.OPTIONS_PANEL.setVisible(!ParticleMain.OPTIONS_PANEL.isVisible());
            }
        });

        add(visibility, BorderLayout.EAST);
        add(new JLabel("Particle System v" + ParticleMain.VERSION_ID + ", By: Connor H. & Jason S."), BorderLayout.CENTER);
    }
}
