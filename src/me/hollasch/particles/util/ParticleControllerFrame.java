package me.hollasch.particles.util;

import me.hollasch.particles.ParticleSystem;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
        JSlider speed = new JSlider(JSlider.HORIZONTAL, 1, 25, 20);

        speed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                host.updateTickRate(source.getValue());
            }
        });
        speed.setMajorTickSpacing(5);
        speed.setPaintTicks(true);

        Hashtable speedLabel = new Hashtable();
        speedLabel.put(12, new JLabel("Update Interval"));
        speed.setLabelTable(speedLabel);
        speed.setPaintLabels(true);

        //================= RESPAWN FREQUENCY ==================

        JSlider respawn = new JSlider(JSlider.HORIZONTAL, 5, 1000, 30);

        respawn.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                host.setRespawnFrequency(-source.getValue() + 995);
            }
        });
        host.setRespawnFrequency(-respawn.getValue() + 995);
        respawn.setMajorTickSpacing(50);
        respawn.setPaintTicks(true);

        Hashtable respawnTable = new Hashtable();
        respawnTable.put(500, new JLabel("Respawn Interval"));
        respawn.setLabelTable(respawnTable);
        respawn.setPaintLabels(true);

        //================= CLEAR SCREEN ==================

        JButton clear = new JButton("Clear Screen");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                host.clear();
            }
        });

        //================= FPS COUNTER ==================

        final JLabel fps = new JLabel();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                fps.setText(host.fps + " FPS");
            }
        }, 50, 50);

        //======================================================

        setLayout(new FlowLayout());
        add(speed);
        add(respawn);
        add(clear);
        add(fps, FlowLayout.LEADING);
    }
}
