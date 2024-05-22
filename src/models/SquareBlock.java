package models;

public class SquareBlock {
    private Position position;
    private final BlockType blockType;
    private boolean cleared;

    public SquareBlock(Position position, BlockType blockType) {
        this.position = position;
        if (blockType == null) throw new IllegalArgumentException("BlockType can't be null");

        this.blockType = blockType;
        this.cleared = false;
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public BlockType getBlockType() {
        return blockType;
    }

    public boolean isCleared() {
        return cleared;
    }
    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    public enum BlockType {
        PLAIN(1),
        ROCKY(2),
        TREE(2),
        PRESERVED_TREE(2);

        private final int fuelToClear;

        BlockType(int fuelToClear) {
            this.fuelToClear = fuelToClear;
        }
    }
}