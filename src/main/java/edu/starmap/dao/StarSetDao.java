package edu.starmap.dao;

import edu.starmap.common.StarInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StarSetDao {
    public static List<StarInfo> getStarInfos(File starSetFile) throws FileNotFoundException {
        Scanner reader = new Scanner(new FileInputStream(starSetFile));
        reader.nextLine();
        List<StarInfo> starInfos = new ArrayList<>();
        while(reader.hasNext()) {
            double mag = reader.nextDouble();
            double ra = reader.nextDouble();
            double dec = reader.nextDouble();
            double x = reader.nextDouble();
            double y = reader.nextDouble();
            double z = reader.nextDouble();
            double Vx = reader.nextDouble();
            double Vy = reader.nextDouble();
            double Vz = reader.nextDouble();
            StarInfo starInfo = new StarInfo(
                    mag,
                    ra,
                    dec,
                    x,
                    y,
                    z,
                    Vx,
                    Vy,
                    Vz
            );
            starInfos.add(starInfo);
        }
        return starInfos;
    }

    private StarSetDao(){}
}
