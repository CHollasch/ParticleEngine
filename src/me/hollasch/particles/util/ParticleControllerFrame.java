package me.hollasch.particles.util;

import me.hollasch.particles.ParticleHost;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class ParticleControllerFrame extends JPanel {

    public ParticleControllerFrame(final ParticleHost host) {
        //================= FRAME UPDATE INTERVAL ==================
        JSlider speed = new JSlider(JSlider.HORIZONTAL, 1, 50, 20);

        speed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                host.updateTickRate(source.getValue());
            }
        });
        speed.setMajorTickSpacing(5);
        speed.setPaintTicks(true);

        Hashtable speedLabel = new Hashtable();
        speedLabel.put(25, new JLabel("Update Interval"));
        speed.setLabelTable(speedLabel);
        speed.setPaintLabels(true);

        //================= RESPAWN FREQUENCY ==================

        JSlider respawn = new JSlider(JSlider.HORIZONTAL, 10, 1000, 30);

        respawn.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                host.setRespawnFrequency(-source.getValue() + 990);
            }
        });
        host.setRespawnFrequency(-respawn.getValue() + 990);
        respawn.setMajorTickSpacing(50);
        respawn.setPaintTicks(true);

        Hashtable respawnTable = new Hashtable();
        respawnTable.put(500, new JLabel("Respawn Interval"));
        respawn.setLabelTable(respawnTable);
        respawn.setPaintLabels(true);

        //======================================================

        add(speed);
        add(respawn);
    }
}
