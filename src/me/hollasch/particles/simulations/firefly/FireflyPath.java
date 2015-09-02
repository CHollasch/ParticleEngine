package me.hollasch.particles.simulations.firefly;

/**
 * @author Connor Hollasch
 * @since 2/8/2015
 */
public class FireflyPath {

    private double sinA;
    private double sinB;
    private double cosA;
    private double cosB;

    private double a;
    private double b;

    public FireflyPath(double b, double a) {
        this.a = a;
        this.b = b;

        sinA = Math.sin(a);
        sinB = Math.sin(b);
        cosA = Math.cos(a);
        cosB = Math.cos(b);
    }

    public double[] rotate(double degrees) {
        double angle = Math.PI * 2 * degrees;

        double sinAngle = Math.sin(angle);
        double cosAngle = Math.cos(angle);

        sinA = Math.sin(b * angle);
        cosA = Math.cos(b * angle);
        sinB = Math.sin(a * angle);
        cosB = Math.cos(a * angle);

        double x = cosAngle * cosA - sinAngle * sinB * sinA;
        double y = sinAngle * cosB;

        return new double[]{x, y};
    }
}
