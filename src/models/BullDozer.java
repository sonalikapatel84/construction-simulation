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

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }
    public Position getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }
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

    public void advance(int stepsToAdvance, Site site, ReportManager reportManager) {
        Position potentialPosition = calculatePotentialPosition(stepsToAdvance);
        if (site.isOutOfBound(potentialPosition)) {
            throw new IllegalStateException("Bulldozer is out of site boundaries and cannot move further.");
        }

        //Now bulldozer is all set to "advance" , hence the below calc
        for(int i=0; i<stepsToAdvance; i++) {

            System.out.printf("This is the %s step of %s steps \n", i, stepsToAdvance);
            Position oneStepAdvance = calculatePotentialPosition(1);
            this.setCurrentPosition((oneStepAdvance));
            System.out.printf("Current position of bulldozer %s, %s, %s \n",
                    this.getCurrentPosition().getX(),
                    this.getCurrentPosition().getY(),
                    this.getCurrentDirection());

            // Grabbing the square block at the new position
            SquareBlock squareBlock = site.getBlockAt(oneStepAdvance);

            if (squareBlock.isCleared()) {
                reportManager.addFuelUsage(1);
            } else {
                switch (squareBlock.getBlockType()) {
                    case PLAIN:
                        System.out.println("Sqaure Block with PLAIN.");
                        reportManager.addFuelUsage(1);
                        squareBlock.setCleared(true);
                        break;
                    case ROCKY:
                        System.out.println("Sqaure Block with ROCKY.");
                        reportManager.addFuelUsage(2);
                        squareBlock.setCleared(true);
                        break;
                    case TREE:
                        System.out.println("Sqaure Block with TREE.");
                        reportManager.addFuelUsage(2);
                        squareBlock.setCleared(true);
                        if (i != stepsToAdvance-1) { // The last step, ie bulldozer will stop.
                            reportManager.incrementPaintDamage();
                        }
                        break;
                    case PRESERVED_TREE:
                        System.out.println("Sqaure Block with PRESERVED TREE.");
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

    public void turnLeft() {
        this.currentDirection = this.currentDirection.turnLeft();
    }
    public void turnRight() {
        this.currentDirection = this.currentDirection.turnRight();
    }
}