package models;

import java.io.File;

public class HelperClass {

    private HelperClass() {}

    public static void exit(Site site, ReportManager reportManager) {
        CommandHistory.printHistory();
        int unclearedSquares = site.countUnclearedSquares();
        reportManager.incrementUnclearedSquares(unclearedSquares);
        reportManager.printReport();
        System.exit(0);
    }

    public static boolean ValidateAndDisplay(String filePath, Site site) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return true;
        }
        System.out.println();
        System.out.println("Welcome to the Aconex site clearing simulator. This is a map of the site: ");
        System.out.println();
        site.showMap();
        System.out.println();
        System.out.println("The bulldozer is currently located at the Northern edge of the site,immediately to the West of the site, and facing East.");
        System.out.println();
        return false;
    }
}
