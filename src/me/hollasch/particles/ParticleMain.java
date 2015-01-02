package me.hollasch.particles;

import me.hollasch.particles.simulations.firework.FireworkSpawnController;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.simulations.snow.SnowSpawnController;
import me.hollasch.particles.simulations.stars.StarSpawnController;
import me.hollasch.particles.util.frame.ParticleControllerFrame;
import me.hollasch.particles.util.frame.ParticleFooterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleMain {

    public static String VERSION_ID = "1.0.0";

    public static JFrame MAIN_FRAME = new JFrame("Particle System v" + VERSION_ID);
    public static JPanel OPTIONS_PANEL;
    public static ParticleMain me;

    public static void main(String[] args) {
        me = new ParticleMain();

        MAIN_FRAME.setSize(new Dimension(1000, 1000));
        MAIN_FRAME.setMinimumSize(new Dimension(480, 640));

        MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //FOR FULLSCREEN
        {
            MAIN_FRAME.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "Fullscreen");
            MAIN_FRAME.getRootPane().getActionMap().put("Fullscreen", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    MAIN_FRAME.setBounds(0, 0, MAIN_FRAME.getToolkit().getScreenSize().width, MAIN_FRAME.getToolkit().getScreenSize().height);
                    MAIN_FRAME.dispose();
                    MAIN_FRAME.setUndecorated(true);
                    MAIN_FRAME.setVisible(true);
                }
            });
        }

        //FOR FULLSCREEN ESCAPE
        {
            MAIN_FRAME.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
            MAIN_FRAME.getRootPane().getActionMap().put("Cancel", new AbstractAction() { //$NON-NLS-1$
                public void actionPerformed(ActionEvent e) {
                    MAIN_FRAME.dispose();
                    MAIN_FRAME.setUndecorated(false);
                    MAIN_FRAME.setSize(1000, 1000);
                    MAIN_FRAME.setVisible(true);
                }
            });
        }

        final ParticleSystem host = new ParticleSystem(5);

        host.addRespawnTask(new SnowSpawnController().setHost(host).setFrequency(1));
        host.addRespawnTask(new FireworkSpawnController().setHost(host).setFrequency(10));
        host.addRespawnTask(new StarSpawnController().setHost(host).setFrequency(10));

        MAIN_FRAME.setLayout(new BorderLayout());

        OPTIONS_PANEL = new ParticleControllerFrame(host);
        MAIN_FRAME.add(OPTIONS_PANEL, BorderLayout.NORTH);
        MAIN_FRAME.add(host, BorderLayout.CENTER);
        MAIN_FRAME.add(new ParticleFooterFrame(host), BorderLayout.PAGE_END);

        MAIN_FRAME.setVisible(true);
    }
}
