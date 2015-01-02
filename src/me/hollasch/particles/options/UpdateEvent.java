package me.hollasch.particles.options;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public interface UpdateEvent<T> {

    public void onUpdate(T option);
}
