package edu.starmap.map;

import edu.starmap.common.StarInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class MapGenerator {

    private final static double PIXEL_TO_MM_RATIO = 6.0;

    private final static double BASE_STAR_DIAMETER = 1.0; // 1 mm
    private final static int IMAGE_WIDTH = 2520;
    private final static int MARGIN = 20;

    private static double baseMag;
    private static double minRa;
    private static double maxRa;
    private static double minDec;
    private static double maxDec;
    private static int imageHeight;

    public static void generateStarMap(String basename, List<StarInfo> starSet) throws IOException {
        setUpBaseParams(starSet);
        BufferedImage map = fillWithStars(createWhitePane(), starSet);
        ImageIO.write(map, "jpeg", new File(basename + ".jpeg"));
    }

    private static BufferedImage fillWithStars(BufferedImage map, List<StarInfo> starSet) {
        Comparator<StarInfo> comparator = Comparator.comparing(h -> h.magnitude);
        starSet.sort(comparator.reversed());

        for (StarInfo star : starSet) {
            drawStar(
                    calculateXPos(star.rightAscension),
                    calculateYPos(star.declination),
                    calculateDiameterInPixels(star.magnitude),
                    calculateColor(star.magnitude),
                    map
            );
        }
        return map;
    }

    private static void drawStar(int x, int y, int diameter, Color color, BufferedImage map) {
        int radius = diameter / 2;
        for (int w = x - radius; w < x + radius; w++) {
            for (int h = y - radius; h < y + radius; h++) {
                double distance = Math.sqrt((w - x) * (w - x) + (h - y) * (h - y));
                if (distance <= radius) {
                    map.setRGB(w + MARGIN, h + MARGIN, color.getRGB());
                }
            }
        }
    }

    private static void setUpBaseParams(List<StarInfo> starSet) {
        baseMag = starSet.stream().mapToDouble(s -> s.magnitude).max().orElse(0.0);
        minRa = starSet.stream().mapToDouble(s -> s.rightAscension).min().orElse(0.0);
        maxRa = starSet.stream().mapToDouble(s -> s.rightAscension).max().orElse(0.0);
        minDec = starSet.stream().mapToDouble(s -> s.declination).min().orElse(0.0);
        maxDec = starSet.stream().mapToDouble(s -> s.declination).max().orElse(0.0);
        imageHeight = (int) (IMAGE_WIDTH * (maxDec - minDec) / (maxRa - minRa));
    }

    private static int calculateDiameterInPixels(double mag) {
        double diff = baseMag - mag;
        double ratio = -0.0077378 * diff * diff * diff + 0.037263 * diff * diff + 1.1416 * diff + 1.0603;
        return (int) (PIXEL_TO_MM_RATIO * BASE_STAR_DIAMETER * ratio);
    }

    private static Color calculateColor(double mag) {
        double diff = (baseMag - mag) * 20; // Color difference multiplier
        return new Color((int) (100 + diff),(int) (100 + diff),(int) (100 + diff));
    }

    // Coordinates for image is upper-left corner. Bottom-right has coordinates (x = 0, y = HEIGHT)
    private static int calculateXPos(double ra) {
        double relativePos = 1 - (ra - minRa) / (maxRa - minRa);
        return (int) (IMAGE_WIDTH * relativePos);
    }

    private static int calculateYPos(double dec) {
        double relativePos = 1 - (dec - minDec) / (maxDec - minDec);
        return (int) (imageHeight * relativePos);
    }


    private static BufferedImage createWhitePane() {
        BufferedImage bi = new BufferedImage(IMAGE_WIDTH + MARGIN * 2, imageHeight + MARGIN * 2,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        return bi;
    }
}
