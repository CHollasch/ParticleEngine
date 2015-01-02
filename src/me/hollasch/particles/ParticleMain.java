package me.hollasch.particles;

import me.hollasch.particles.firework.FireworkRespawnTask;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.snow.SnowSpawnController;
import me.hollasch.particles.util.ParticleControllerFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleMain {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Particle Framework");
        frame.setSize(new Dimension(1000, 1000));
        frame.setMinimumSize(new Dimension(480, 640));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ParticleSystem host = new ParticleSystem(5);
        host.addRespawnTask(new SnowSpawnController().setHost(host).setFrequency(1));
        host.addRespawnTask(new FireworkRespawnTask().setHost(host).setFrequency(10));

        frame.setLayout(new BorderLayout());

        frame.add(new ParticleControllerFrame(host), BorderLayout.NORTH);
        frame.add(host, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
