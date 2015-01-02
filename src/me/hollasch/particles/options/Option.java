package me.hollasch.particles.options;

/**
 * @author Connor Hollasch
 * @since 1/2/2015
 */
public interface Option<T> {

    public String getDescription();

    public T getValue();
}
