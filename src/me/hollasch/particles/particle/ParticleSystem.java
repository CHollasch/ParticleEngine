package me.hollasch.particles.particle;

import me.hollasch.particles.respawn.Respawnable;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleSystem extends JPanel {

    private int millisTickRate = 50;

    private HashSet<Particle> alive = new HashSet<Particle>();
    private ConcurrentHashMap<Respawnable, Timer> respawnTasks = new ConcurrentHashMap<Respawnable, Timer>();

    private java.util.Timer tickTask;

    public ParticleSystem(int updateInterval) {
        updateTickRate(updateInterval);

        this.millisTickRate = updateInterval;
    }

    private HashSet<Particle> queuedForSpawn = new HashSet<Particle>();
    private HashSet<Particle> queuedForDespawn = new HashSet<Particle>();

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
    }

    public void setRespawnFrequency(Respawnable respawn, int frequency) {
        respawn = matches(respawn.getName());

        respawnTasks.get(respawn).cancel();

        Timer make = new Timer();
        make.scheduleAtFixedRate(new SpawnRateTask(respawn), frequency, frequency);

        respawnTasks.put(respawn, make);
    }

    private Respawnable matches(String name) {
        for (Respawnable key : respawnTasks.keySet()) {
            if (key.getName().equals(name))
                return key;
        }
        return null;
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
            for (Particle p : alive) {
                Color old = g.getColor();
                g.setColor(Color.white);
                p.paint(this, g);
                g.setColor(old);

                if (p.dead || p.getCenterX() > getWidth() || p.getCenterX() < 0 || p.getCenterY() > getHeight() || p.getCenterY() < 0)
                    queuedForDespawn.add(p);
            }

            alive.removeAll(queuedForDespawn);
            queuedForDespawn.clear();

            alive.addAll(queuedForSpawn);
            queuedForSpawn.clear();
        }
    }

    public void addRespawnTask(Respawnable task, int frequency) {
        Timer make = new Timer();
        make.scheduleAtFixedRate(new SpawnRateTask(task), frequency, frequency);

        respawnTasks.put(task, make);
    }

    public void clear() {
        queuedForDespawn.addAll(alive);
    }

    public Set<Respawnable> getRespawnTasks() {
        return respawnTasks.keySet();
    }

    public void addRespawnTask(Respawnable respawnable) {
        int min = (int) respawnable.getRespawnRateRange().getMinimum();
        int max = (int) respawnable.getRespawnRateRange().getMaximum();

        addRespawnTask(respawnable, ((max - min) / 2));
    }

    private class SpawnRateTask extends TimerTask {
        private Respawnable target;

        private SpawnRateTask(Respawnable target) {
            this.target = target;
        }

        public void run() {
            synchronized (ParticleSystem.this) {
                target.tick++;
                if (target.spawn())
                    target.run();
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
