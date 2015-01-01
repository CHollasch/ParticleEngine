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
        tickTask = new java.util.Timer();
        tickTask.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized (ParticleSystem.this) {
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
                synchronized (ParticleSystem.this) {
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
                synchronized (ParticleSystem.this) {
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
                synchronized (ParticleSystem.this) {
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

    public int fps;

    private long nextSecond = System.currentTimeMillis() + 100;
    private int frameInCurrentSecond = 0;

    public void paint(Graphics g) {
        long currentTime = System.currentTimeMillis();
        if (currentTime > nextSecond) {
            nextSecond += 100;
            fps = frameInCurrentSecond;
            frameInCurrentSecond = 0;
        }
        frameInCurrentSecond++;

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

    public void clear() {
        alive.clear();
    }
}
