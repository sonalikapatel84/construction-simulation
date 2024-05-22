package models;

import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a site with a two-dimensional map consisting of square blocks.
 */
public class Site {
    private List<List<SquareBlock>> map;

    /**
     * Constructs a new models.Site object by reading the provided file. Each line in the file is converted to a list of Character objects,
     * creating a two-dimensional structure that represents the site map.
     *
     * @param file The path of the file to be read. This file provides the site layout.
     * @throws IOException If there is an error reading the file.
     */
    public Site(String file) throws IOException {
        map = new ArrayList<>();
        AtomicInteger yCoord = new AtomicInteger(0); // Setting this as row number
        Files.lines(Paths.get(file)).forEach(
            line -> {
                List<SquareBlock> row = new ArrayList<>();
                AtomicInteger xCoord = new AtomicInteger(0); // Setting this as column number
                line.chars().forEach(ch -> {
                    SquareBlock.BlockType blockType;
                    switch (ch) {
                        case 'o':
                            blockType = SquareBlock.BlockType.PLAIN;
                            break;
                        case 'r':
                            blockType = SquareBlock.BlockType.ROCKY;
                            break;
                        case 't':
                            blockType = SquareBlock.BlockType.TREE;
                            break;
                        case 'T':
                            blockType = SquareBlock.BlockType.PRESERVED_TREE;
                            break;
                        case ' ':
                            if (ch == ' ') return;
                        default:
                            System.out.println("char ch ->   " + ch);
                            throw new IllegalArgumentException("Invalid block type: " + (char)ch);
                    }
                    Position position = new Position(yCoord.get(), xCoord.getAndIncrement());
                    SquareBlock newBlock = new SquareBlock(position, blockType);
                    newBlock.setCleared(false);
                    row.add(newBlock);
                });
                map.add(row);
                yCoord.getAndIncrement();
            }
        );
    }

    /**
     * This method counts the number of uncleared squares in the map.
     *
     * @return The number of uncleared squares.
     */
    public int countUnclearedSquares() {
        return (int) map.stream()
                .flatMap(List::stream)
                .filter(square -> !square.isCleared())
                .count();
    }

    /**
     * Returns the models.SquareBlock at the specified position on the site map.
     *
     * @param position The position of the models.SquareBlock to retrieve.
     * @return The models.SquareBlock at the specified position.
     * @throws IndexOutOfBoundsException If the position is out of site boundaries.
     */
    public SquareBlock getBlockAt(Position position) {
        return this.map.get(position.getX()).get(position.getY());
    }

    /**
     * Checks if a given position is out of bounds of the map.
     *
     * @param newPosition The position to check.
     * @return {@code true} if the position is out of bounds, {@code false} otherwise.
     */
    public boolean isOutOfBound(Position newPosition) {
        if (newPosition.getX() < 0 || newPosition.getY() < 0 || newPosition.getX() >= map.size()
                || newPosition.getY() >= map.get(0).size()) {
            return true;
        }
        return false;
    }

    /**
     * Displays the map of the site.
     */
    public void showMap() {
        for (List<SquareBlock> row : map) {
            for (SquareBlock block : row) {
                System.out.print("| ");
                if (block == null) {
                    System.out.print("-");
                } else {
                    switch (block.getBlockType()) {
                        case PLAIN:
                            System.out.printf("o(%d,%d)", block.getPosition().getX(), block.getPosition().getY());
                            break;
                        case ROCKY:
                            System.out.printf("r(%d,%d)", block.getPosition().getX(), block.getPosition().getY());
                            break;
                        case TREE:
                            System.out.printf("t(%d,%d)", block.getPosition().getX(), block.getPosition().getY());
                            break;
                        case PRESERVED_TREE:
                            System.out.printf("T(%d,%d)", block.getPosition().getX(), block.getPosition().getY());
                            break;
                    }
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }
}