package me.hollasch.particles.particle;

import me.hollasch.particles.debug.OptionDebugManager;
import me.hollasch.particles.options.Option;
import me.hollasch.particles.options.Source;
import me.hollasch.particles.respawn.Respawnable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Connor on 12/31/2014.
 */
public class ParticleSystem extends JPanel {

    private long ticks = 0;
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

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);

        if (debug) {
            g.setColor(Color.darkGray);

            for (int i = 0; i < getWidth(); i += 10) {
                g.drawLine(i, 0, i, getHeight());
            }

            for (int i = 0; i < getHeight(); i += 10) {
                g.drawLine(0, i, getWidth(), i);
            }

            g.setColor(Color.white);

            int textPos = 0;
            g.drawString("-- Debug Mode Enabled --", 0, textPos+=12);
            g.drawString("  Particle Count (" + alive.size() + ")", 0, textPos+=20);
            g.drawString("  Engine Tick Number (" + ticks + ")", 0, textPos+=12);
            g.drawString("  Queued Particles", 0, textPos+=12);
            g.drawString("    Queued For Despawn (" + queuedForDespawn.size() + ")", 0, textPos+=12);
            g.drawString("    Queued For Spawning (" + queuedForSpawn.size() + ")", 0, textPos+=12);
            g.drawString("  Options", 0, textPos+=12);
            for (Option opt : OptionDebugManager.getAllOptions()) {
                g.drawString("    " + opt.getDescription() + " = " + opt.getValue().toString(), 0, textPos += 12);
            }
            g.drawString("------------------------------------", 0, textPos+20);
        }

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

    private boolean debug;

    public void toggleDebugMode() {
        debug = !debug;
    }

    private class SpawnRateTask extends TimerTask {
        private Respawnable target;

        private SpawnRateTask(Respawnable target) {
            this.target = target;
        }

        public void run() {
            synchronized (ParticleSystem.this) {
                if (millisTickRate > 30)
                    return;

                target.tick++;
                if (target.spawn())
                    target.run();

                ticks++;
            }
        }
    }

    private class TickRateTask extends TimerTask {
        public void run() {
            synchronized (ParticleSystem.this) {
                if (millisTickRate > 30)
                    return;

                for (Particle p : alive) {
                    p.tick();
                }

                repaint();
            }
        }
    }
}
