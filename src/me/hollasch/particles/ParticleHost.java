package me.hollasch.particles;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.TimerTask;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleHost extends JPanel {

    private int millisTickRate = 50;

    private HashSet<Particle> alive = new HashSet<Particle>();
    private HashSet<Respawnable> respawnTasks = new HashSet<Respawnable>();

    private java.util.Timer tickTask;
    private java.util.Timer spawnTask;

    public ParticleHost(int updateInterval) {
        tickTask = new java.util.Timer();
        tickTask.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (ParticleHost.this) {
                    for (Particle p : alive) {
                        p.tick();
                    }

                    repaint();
                }
            }
        }, updateInterval, updateInterval);

        spawnTask = new java.util.Timer();
        spawnTask.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (ParticleHost.this) {
                    for (Respawnable respawn : respawnTasks) {
                        respawn.tick++;
                        if (respawn.spawn())
                            respawn.run();
                    }
                }
            }
        }, 1, 1);

        this.millisTickRate = updateInterval;
    }

    private HashSet<Particle> queuedForSpawn = new HashSet<Particle>();

    public int getTickRate() {
        return millisTickRate;
    }

    public void updateTickRate(int now) {
        tickTask.cancel();
        tickTask = new java.util.Timer();
        tickTask.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (ParticleHost.this) {
                    for (Particle p : alive) {
                        p.tick();
                    }

                    repaint();
                }
            }
        }, now, now);
        this.millisTickRate = now;
        updateSpawnRate();
    }

    private void updateSpawnRate() {
        spawnTask.cancel();
        spawnTask = new java.util.Timer();
        spawnTask.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (ParticleHost.this) {
                    for (Respawnable respawn : respawnTasks) {
                        respawn.tick++;
                        if (respawn.spawn())
                            respawn.run();
                    }
                }
            }
        }, millisTickRate, millisTickRate);
    }

    public void setRespawnFrequency(int frequency) {
        for (Respawnable respawn : respawnTasks) {
            respawn.setFrequency(frequency);
        }
    }

    public void addParticle(Particle particle) {
        queuedForSpawn.add(particle);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
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

    public void addRespawnTask(Respawnable task) {
        respawnTasks.add(task);
    }
}
