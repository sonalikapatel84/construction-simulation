import models.*;


import java.io.IOException;
import java.util.Scanner;

public class SiteSimulation {

    public static void main(String[] args) throws IOException {

        String filePath = args[0];
        Site site = new Site(filePath);
        if (HelperClass.ValidateAndDisplay(filePath, site)) return;

        int x_coordinate =0;
        int y_coordinate =0;
        BullDozer bulldozer = new BullDozer(new Position(x_coordinate, y_coordinate), Direction.EAST);
        ReportManager reportManager = new ReportManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
            String command = scanner.nextLine();

            String[] userCommandString = command.toLowerCase().split(" ");
            String userCommand = userCommandString[0];
            String numberOfStepsToAdvance = String.valueOf(0);
            if (userCommandString.length > 1) {
                numberOfStepsToAdvance = userCommandString[1];
            }

            switch (userCommand) {
                case "advance":
                case "a":

                    CommandHistory.addCommand("advance " + numberOfStepsToAdvance);
                    if (numberOfStepsToAdvance == null || !numberOfStepsToAdvance.matches("\\d+")) {
                        System.out.println("The 'advance' command should be followed by a positive integer.");
                        break;
                    }
                    int stepsToAdvance = Integer.parseInt(numberOfStepsToAdvance);
                    try {
                        bulldozer.advance(stepsToAdvance, site, reportManager);
                        System.out.println("Advance command selected to move " + stepsToAdvance + " steps.");
                    } catch (Exception e) {
                        System.out.println("An error occurred while executing the command: " + e.getMessage());
                        HelperClass.exit(site, reportManager);
                    }
                    break;
                case "left":
                case "l":
                    CommandHistory.addCommand("turn left");
                    bulldozer.turnLeft();
                    break;
                case "right":
                case "r":
                    CommandHistory.addCommand("turn right");
                    bulldozer.turnRight();
                    break;
                case "quit":
                case "q":
                    CommandHistory.addCommand("quit");
                    HelperClass.exit(site, reportManager);
                default:
                    System.out.println("Invalid command! Please enter a valid command.");
            }
        }
    }
}