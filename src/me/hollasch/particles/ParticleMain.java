package me.hollasch.particles;

import me.hollasch.particles.simulations.firework.FireworkSpawnController;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.simulations.snow.SnowSpawnController;
import me.hollasch.particles.simulations.stars.StarSpawnController;
import me.hollasch.particles.util.ParticleControllerFrame;
import me.hollasch.particles.util.ParticleFooterFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleMain {

    public static String VERSION_ID = "1.0.0";

    public static JFrame MAIN_FRAME = new JFrame("Particle System v" + VERSION_ID);
    public static JPanel OPTIONS_PANEL;

    public static void main(String[] args) {
        MAIN_FRAME.setSize(new Dimension(1000, 1000));
        MAIN_FRAME.setMinimumSize(new Dimension(480, 640));

        MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ParticleSystem host = new ParticleSystem(5);
        host.addRespawnTask(new SnowSpawnController().setHost(host).setFrequency(1));
        host.addRespawnTask(new FireworkSpawnController().setHost(host).setFrequency(10));
        host.addRespawnTask(new StarSpawnController().setHost(host).setFrequency(10));

        MAIN_FRAME.setLayout(new BorderLayout());

        OPTIONS_PANEL = new ParticleControllerFrame(host);
        MAIN_FRAME.add(OPTIONS_PANEL, BorderLayout.NORTH);
        MAIN_FRAME.add(host, BorderLayout.CENTER);
        MAIN_FRAME.add(new ParticleFooterFrame(), BorderLayout.PAGE_END);

        MAIN_FRAME.setVisible(true);
    }
}
