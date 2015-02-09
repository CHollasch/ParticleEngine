package me.hollasch.particles.simulations.firefly;

/**
 * @author Connor Hollasch
 * @since 2/8/2015
 */
public class FireflyPath {

    private double sinPhi;
    private double sinTheta;
    private double cosPhi;
    private double cosTheta;

    private double a;
    private double b;

    private boolean random;

    public FireflyPath(double a, double b, boolean random) {
        this.a = a;
        this.b = b;

        this.random = random;

        sinPhi = Math.sin(b);
        sinTheta = Math.sin(a);
        cosPhi = Math.cos(b);
        cosTheta = Math.cos(a);
    }

    public double[] rotate(double t) {
        double angle = Math.PI * 2 * t;

        double sinAngle = Math.sin(angle);
        double cosAngle = Math.cos(angle);

        if (random) {
            sinPhi = Math.sin(b * angle);
            cosPhi = Math.cos(b * angle);
            sinTheta = Math.sin(a * angle);
            cosTheta = Math.cos(a * angle);
        }

        double x = cosAngle * cosPhi - sinAngle * sinTheta * sinPhi;
        double y = sinAngle * cosTheta;

        return new double[]{x, y};
    }
}
