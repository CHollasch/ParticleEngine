package me.hollasch.particles;

import me.hollasch.particles.api.ParticleRespawnableQueue;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.ui.ParticleControllerFrame;
import me.hollasch.particles.ui.ParticleFooterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleFrameHandler {

    public static String VERSION_ID = "1.0.0";

    public static JFrame MAIN_FRAME = new JFrame("Particle System v" + VERSION_ID);

    public static JPanel OPTIONS_PANEL;
    private static JPanel FOOTER_PANEL;

    private static ParticleSystem PARTICLE_SYSTEM;

    public static ParticleFrameHandler me;

    public static void execute(String[] args) {
        me = new ParticleFrameHandler();

        MAIN_FRAME.setSize(new Dimension(1000, 1000));
        MAIN_FRAME.setMinimumSize(new Dimension(480, 640));

        MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //FOR FULLSCREEN
        MAIN_FRAME.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "Fullscreen");
        MAIN_FRAME.getRootPane().getActionMap().put("Fullscreen", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (e != null) {
                    FOOTER_PANEL.setVisible(false);
                }
                MAIN_FRAME.setBounds(0, 0, MAIN_FRAME.getToolkit().getScreenSize().width, MAIN_FRAME.getToolkit().getScreenSize().height);
                MAIN_FRAME.dispose();
                MAIN_FRAME.setUndecorated(true);
                MAIN_FRAME.setVisible(true);
            }
        });

        //FOR FULLSCREEN ESCAPE
        MAIN_FRAME.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        MAIN_FRAME.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                FOOTER_PANEL.setVisible(true);
                MAIN_FRAME.dispose();
                MAIN_FRAME.setUndecorated(false);
                MAIN_FRAME.setSize(1000, 1000);
                MAIN_FRAME.setVisible(true);
            }
        });

        PARTICLE_SYSTEM = new ParticleSystem(5);

        ParticleRespawnableQueue.link(PARTICLE_SYSTEM);
        ParticleRespawnableQueue.finalizeControllers();

        MAIN_FRAME.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                PARTICLE_SYSTEM.clear();
            }

            public void componentShown(ComponentEvent e) {
                PARTICLE_SYSTEM.unfreeze();
            }

            public void componentHidden(ComponentEvent e) {
                PARTICLE_SYSTEM.freeze();
            }

            public void componentMoved(ComponentEvent e) {}
        });

        MAIN_FRAME.setLayout(new BorderLayout());

        OPTIONS_PANEL = new ParticleControllerFrame(getActiveParticleSystem());
        MAIN_FRAME.add(OPTIONS_PANEL, BorderLayout.NORTH);
        MAIN_FRAME.add(getActiveParticleSystem(), BorderLayout.CENTER);
        MAIN_FRAME.add(FOOTER_PANEL = new ParticleFooterFrame(getActiveParticleSystem()), BorderLayout.PAGE_END);

        MAIN_FRAME.setVisible(true);
    }

    public static ParticleSystem getActiveParticleSystem() {
        return PARTICLE_SYSTEM;
    }
}
