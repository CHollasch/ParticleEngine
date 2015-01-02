package me.hollasch.particles.util;

/**
 * @author Connor Hollasch
 * @since 12/31/2014
 */
public class Range {

    private double min;
    private double max;

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double randomDouble() {
        return (Math.random() * (max - min)) + min;
    }

    public int randomInt() {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public double getMinimum() {
        return min;
    }

    public double getMaximum() {
        return max;
    }
}
