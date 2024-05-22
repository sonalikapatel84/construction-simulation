package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHistory {
    private static List<String> commandHistory = new ArrayList<>();

    public static void addCommand(String command) {
        commandHistory.add(command);
    }

    public static void printHistory() {
        System.out.println();
        System.out.println("\"The simulation has ended at your request. These are the commands you issued:\"");
        System.out.println();
        String joined = commandHistory.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        System.out.println(joined);
        System.out.println();
    }
}