package me.hollasch.particles.simulations.snow;

/**
 * @author Connor Hollasch
 * @since 2/8/2015
 */
public class SimpleTrigLookup {

    public static double[] SIN_VALUES = new double[360];
    public static double[] COS_VALUES = new double[360];

    static {
        for (int i = 0; i < 360; i++) {
            SIN_VALUES[i] = Math.sin(Math.toRadians(i));
            COS_VALUES[i] = Math.cos(Math.toRadians(i));
        }
    }
}
