package edu.starmap.speed;

import edu.starmap.common.StarInfo;

import java.util.List;

public class RadialVelocityCalculator {

    public static double calculateMeanRadialVelocity(List<StarInfo> starSet) {
        return starSet.stream()
                .mapToDouble(RadialVelocityCalculator::calculateRadialVelocity)
                .average()
                .orElseThrow();

    }
    private static double calculateRadialVelocity(StarInfo s) {
        double distance = Math.sqrt(s.x * s.x + s.y * s.y + s.z * s.z);
        // Vproj = V * U / |U| = Vx * (Ux / |U|) + Vy * (Uy / |U|) + Vz * (Uz / |U|)
        return (s.Vx * s.x + s.Vy * s.y + s.Vz *s.z) / distance;
    }

    private RadialVelocityCalculator(){}
}
