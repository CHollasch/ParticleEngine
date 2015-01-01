package me.hollasch.particles.test;

import me.hollasch.particles.ParticleHost;
import me.hollasch.particles.firework.FireworkRespawnTask;
import me.hollasch.particles.util.ParticleControllerFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Particle test framework");
        frame.setSize(new Dimension(1000, 1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ParticleHost host = new ParticleHost(5);
        //host.addRespawnTask(new SnowSpawnController().setRandomDirection(new Range(-.25, .25)).setRandomSize(new Range(1, 5)).setHost(host).setFrequency(5));
        host.addRespawnTask(new FireworkRespawnTask().setHost(host).setFrequency(10));

        frame.setLayout(new BorderLayout());

        frame.add(new ParticleControllerFrame(host), BorderLayout.NORTH);
        frame.add(host, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
