package edu.starmap;

import edu.starmap.common.StarInfo;
import edu.starmap.dao.StarSetDao;
import edu.starmap.map.MapGenerator;
import edu.starmap.speed.RadialVelocityCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static edu.starmap.dao.StarSetDao.getStarInfos;
import static edu.starmap.map.MapGenerator.generateStarMap;
import static edu.starmap.speed.RadialVelocityCalculator.calculateMeanRadialVelocity;

public class Praktikum5bMainRunner
{
    private final static String BASE_FOLDER = "star-info";
    private final static List<String> STAR_SETS_TO_ANALYZE = List.of(
            "548119",
            "548202",
            "548262",
            "548478"
    );
    public static void main(String[] args) throws IOException {
        for(String starSetPath : STAR_SETS_TO_ANALYZE) {
            analyzeStarSet(starSetPath);
        }
    }

    private static void analyzeStarSet(String basename) throws IOException {
        System.out.println(basename + ":");
        File starSetFile = new File(BASE_FOLDER + "/" + basename + ".txt");
        List<StarInfo> starSet = getStarInfos(starSetFile);

        double meanRadialVelocity = calculateMeanRadialVelocity(starSet);
        System.out.println("Mean radial velocity: " + meanRadialVelocity + " km/s");

        generateStarMap(basename, starSet);
    }
}
