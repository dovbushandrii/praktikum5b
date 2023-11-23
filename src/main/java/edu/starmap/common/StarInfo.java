package edu.starmap.common;

public class StarInfo {
    public double magnitude;
    public double rightAscension;
    public double declination;
    public double x;
    public double y;
    public double z;
    public double Vx;
    public double Vy;
    public double Vz;

    public StarInfo(
            double magnitude,
            double rightAscension,
            double declination,
            double x,
            double y,
            double z,
            double vx,
            double vy,
            double vz
    ) {
        this.magnitude = magnitude;
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.x = x;
        this.y = y;
        this.z = z;
        Vx = vx;
        Vy = vy;
        Vz = vz;
    }
}
