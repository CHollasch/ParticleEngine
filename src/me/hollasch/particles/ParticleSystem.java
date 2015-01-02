package me.hollasch.particles;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.TimerTask;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleSystem extends JPanel {

    private int millisTickRate = 50;

    private HashSet<Particle> alive = new HashSet<Particle>();
    private HashSet<Respawnable> respawnTasks = new HashSet<Respawnable>();

    private java.util.Timer tickTask;
    private java.util.Timer spawnTask;

    public ParticleSystem(int updateInterval) {
        updateTickRate(updateInterval);
        updateSpawnRate();

        this.millisTickRate = updateInterval;
    }

    private HashSet<Particle> queuedForSpawn = new HashSet<Particle>();

    public int getTickRate() {
        return millisTickRate;
    }

    public void updateTickRate(int now) {
        if (tickTask != null) {
            tickTask.cancel();
        }

        tickTask = new java.util.Timer();
        tickTask.scheduleAtFixedRate(new TickRateTask(), now, now);
        this.millisTickRate = now;
        updateSpawnRate();
    }

    private void updateSpawnRate() {
        if (spawnTask != null) {
            spawnTask.cancel();
        }

        spawnTask = new java.util.Timer();
        spawnTask.scheduleAtFixedRate(new SpawnRateTask(), millisTickRate, millisTickRate);
    }

    public void setRespawnFrequency(int frequency) {
        for (Respawnable respawn : respawnTasks) {
            respawn.setFrequency(frequency);
        }
    }

    public void addParticle(Particle particle) {
        queuedForSpawn.add(particle);
    }

    private Color backgroundColor;

    public void paint(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);

        synchronized (this) {
            HashSet<Particle> remove = new HashSet<Particle>();

            for (Particle p : alive) {
                Color old = g.getColor();
                g.setColor(Color.white);
                p.paint(this, g);
                g.setColor(old);

                if (p.dead || p.getCenterX() > getWidth() || p.getCenterX() < 0 || p.getCenterY() > getHeight() || p.getCenterY() < 0)
                    remove.add(p);
            }

            alive.removeAll(remove);
            alive.addAll(queuedForSpawn);
            queuedForSpawn.clear();
        }
    }

    public static Color[] colors = {Color.blue, Color.green, Color.magenta, Color.cyan, Color.orange, Color.pink, Color.red, Color.yellow, null};

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public void addRespawnTask(Respawnable task) {
        respawnTasks.add(task);
    }

    public void clear() {
        alive.clear();
    }

    private class SpawnRateTask extends TimerTask {
        public void run() {
            synchronized (ParticleSystem.this) {
                for (Respawnable respawn : respawnTasks) {
                    respawn.tick++;
                    if (respawn.spawn())
                        respawn.run();
                }
            }
        }
    }

    private class TickRateTask extends TimerTask {
        public void run() {
            synchronized (ParticleSystem.this) {
                for (Particle p : alive) {
                    p.tick();
                }

                repaint();
            }
        }
    }
}
