package main.scorers;

import main.building.*;
import main.space.*;

public class WoodScorer extends Scorer {

    public WoodScorer(Building building) {
        super(building);
    }

    @Override
    public MaterialScore score() {
        if (!getBuilding().isValid()) {
            return new MaterialScore(Material.WOOD, 0);
        }

        int totalScore = 0;

        for (int row = 1; row <= Building.MAX_ROWS; row++) {
            for (int col = 1; col <= Building.MAX_COLS; col++) {
                Space space = Space.from(Row.at(row), Col.at(col));
                DiceStack stack = getBuilding().getStack(space);
                int height = stack.getHeight();

                for (int level = 1; level <= height; level++) {
                    Die die = stack.getDie(level);
                    if (die.getMaterial() != Material.WOOD)
                        continue;

                    int adjacentWood = 0;

                    // 1. Adjacent spaces (same level)
                    adjacentWood += countWoodAt(row - 1, col, level); // up
                    adjacentWood += countWoodAt(row + 1, col, level); // down
                    adjacentWood += countWoodAt(row, col - 1, level); // left
                    adjacentWood += countWoodAt(row, col + 1, level); // right

                    // 2. Above/below in same stack (stacked adjacency)
                    adjacentWood += countWoodAt(row, col, level - 1); // below
                    adjacentWood += countWoodAt(row, col, level + 1); // above

                    totalScore += 2 * adjacentWood;
                }
            }
        }

        return new MaterialScore(Material.WOOD, totalScore);
    }

    @SuppressWarnings("unused")
    private int countWoodAt(int row, int col, int level) {
        if (row < 1 || row > Building.MAX_ROWS || col < 1 || col > Building.MAX_COLS)
            return 0;

        if (level < 1)
            return 0;

        Space space = Space.from(Row.at(row), Col.at(col));
        DiceStack stack = getBuilding().getStack(space);

        if (level > stack.getHeight())
            return 0;

        Die die = stack.getDie(level);
        return 1;
    }
}
