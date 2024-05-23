package models;

import java.util.HashMap;
import java.util.Map;

public class ReportManager {
    private final Map<String, Integer> reportItems;

    public ReportManager() {
        this.reportItems = new HashMap<>();
        this.reportItems.put("communication overhead", 0);
        this.reportItems.put("fuel usage", 0);
        this.reportItems.put("uncleared squares", 0);
        this.reportItems.put("destruction of protected tree", 0);
        this.reportItems.put("paint damage to bulldozer", 0);
    }

    public void addFuelUsage(int fuel) {
        reportItems.put("fuel usage", reportItems.get("fuel usage") + fuel);
    }

    public void incrementCommunicationOverhead() {
        reportItems.put("communication overhead", reportItems.get("communication overhead") + 1);
    }

    public void incrementProtectedTreeDestruction() {
        reportItems.put("destruction of protected tree", reportItems.get("destruction of protected tree") + 10);
    }

    public void incrementPaintDamage() {
        reportItems.put("paint damage to bulldozer", reportItems.get("paint damage to bulldozer") + 2);
    }

    public void incrementUnclearedSquares(int unclearedSquares) {
        reportItems.put("uncleared squares", reportItems.get("uncleared squares") + unclearedSquares * 3);
    }

    /**
     * Adds a cost to a specific item. Throws an exception if the item is not allowed.
     * @param costItem Cost title
     * @param cost Cost Amount
     */
    public void addCostItem(String costItem, int cost) {
        if (!reportItems.containsKey(costItem)) {
            throw new IllegalArgumentException(costItem + " is not a valid cost item");
        }
        reportItems.put(costItem, reportItems.get(costItem) + cost);
    }

    /**
     * Generates and prints the cost report.
     */
    public void printReport() {
        System.out.println("The costs for this land clearing operation were:");
        System.out.format("%-32s%-8s%-4s%n", "Item", "Quantity", "Cost");
        int total = 0;

        for (Map.Entry<String, Integer> costItem : reportItems.entrySet()) {
            int cost = costItem.getValue();
            total += cost;
            System.out.format("%-32s%-8d%-4d%n", costItem.getKey(), cost, cost);
        }

        System.out.println("----");
        System.out.format("%-32s%-8s%-4d%n", "Total", "", total);
        System.out.println();
        System.out.format("Thank you for using the Aconex site clearing simulator");

    }

    public int getFuelUsage() {
        return reportItems.get("fuel usage");
    }

    public int getPaintDamageCount() {
        return reportItems.get("paint damage to bulldozer");
    }

    public int getProtectedTreeDestructionCount() {
        return reportItems.get("destruction of protected tree");
    }
}