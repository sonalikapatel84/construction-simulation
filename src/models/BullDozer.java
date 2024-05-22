package models;

/**
 * Represents a bulldozer in a land clearing operation.
 */
public class BullDozer {
    private Position currentPosition;
    private Direction currentDirection;

    public BullDozer(Position initialPosition, Direction initialDirection) {
        this.currentPosition = initialPosition;
        this.currentDirection = initialDirection;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }
    /**
     * Calculates the potential position of the bulldozer after advancing a certain number of steps.
     *
     * @param steps The number of steps to advance the bulldozer.
     * @return A Position object representing the potential position of the bulldozer.
     * @throws IllegalArgumentException if an invalid direction is encountered.
     */
    public Position calculatePotentialPosition(int steps) {
        switch (this.currentDirection) {
            case NORTH:
                return new Position(this.currentPosition.x - steps, this.currentPosition.y);
            case SOUTH:
                return new Position(this.currentPosition.x + steps, this.currentPosition.y);
            case WEST:
                return new Position(this.currentPosition.x, this.currentPosition.y - steps);
            case EAST:
                return new Position(this.currentPosition.x, this.currentPosition.y + steps);
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }

    /**
     * Advances the bulldozer by the given number of steps.
     *
     * @param stepsToAdvance The number of steps to advance the bulldozer.
     * @param site The site on which the bulldozer is operating.
     * @param reportManager The report manager to track fuel usage and other costs.
     * @throws IllegalStateException if the bulldozer is out of site boundaries and cannot move further.
     * @throws IllegalArgumentException if an invalid block type is encountered.
     */
    public void advance(int stepsToAdvance, Site site, ReportManager reportManager) {
        Position potentialPosition = calculatePotentialPosition(stepsToAdvance);
        if (site.isOutOfBound(potentialPosition)) {
            throw new IllegalStateException("Bulldozer is out of site boundaries and cannot move further.");
        }

        //Now bulldozer is all set to "advance" , hence the below calc
        for(int i=0; i<stepsToAdvance; i++) {

            Position oneStepAdvance = calculatePotentialPosition(1);
            this.setCurrentPosition((oneStepAdvance));

            // Grabbing the square block at the new position
            SquareBlock squareBlock = site.getBlockAt(oneStepAdvance);

            if (squareBlock.isCleared()) {
                reportManager.addFuelUsage(1);
            } else {
                switch (squareBlock.getBlockType()) {
                    case PLAIN:
                        reportManager.addFuelUsage(1);
                        squareBlock.setCleared(true);
                        break;
                    case ROCKY:
                        reportManager.addFuelUsage(2);
                        squareBlock.setCleared(true);
                        break;
                    case TREE:
                        reportManager.addFuelUsage(2);
                        squareBlock.setCleared(true);
                        if (i != stepsToAdvance-1) { // The last step, ie bulldozer will stop.
                            reportManager.incrementPaintDamage();
                        }
                        break;
                    case PRESERVED_TREE:
                        reportManager.incrementProtectedTreeDestruction();
                        System.out.println("Attempt to remove a protected tree. Exiting game.");
                        HelperClass.exit(site, reportManager);
                        break;

                    default:
                        throw new IllegalArgumentException("Invalid square block");
                }
            }
        }
    }

    /**
     * Turns the bulldozer to the left by changing its current direction.
     */
    public void turnLeft() {
        this.currentDirection = this.currentDirection.turnLeft();
    }
    /**
     * Turns the bulldozer to the right by changing its current direction.
     */
    public void turnRight() {
        this.currentDirection = this.currentDirection.turnRight();
    }
}