package me.hollasch.particles.api;

import me.hollasch.particles.ParticleFrameHandler;
import me.hollasch.particles.particle.ParticleSystem;
import me.hollasch.particles.respawn.Respawnable;

import java.awt.*;
import java.util.Map;

public class ParticleAPI {

    public static final int getEngineWidth() {
        return ParticleFrameHandler.getActiveParticleSystem().getWidth();
    }

    public static final int getEngineHeight() {
        return ParticleFrameHandler.getActiveParticleSystem().getHeight();
    }

    public static ParticleSystem getParticleSystem() {
        return ParticleFrameHandler.getActiveParticleSystem();
    }

    public static boolean isSubscribed(Respawnable respawnable) {
        return ParticleFrameHandler.getActiveParticleSystem().getRespawnTasks().contains(respawnable);
    }

    public static Map<String, Object> getDebuggingInformation() {
        return ParticleFrameHandler.getActiveParticleSystem().getDebuggingInformation();
    }

    private static Point mouseLocation;

    public static void updateMouseLocation(Point point) {
        ParticleAPI.mouseLocation = point;
    }

    public static Point getMouseLocation() {
        return mouseLocation;
    }
}
