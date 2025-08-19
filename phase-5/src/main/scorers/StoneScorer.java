package main.scorers;

import main.building.Building;
import main.building.Die;
import main.building.Material;
import main.building.DiceStack;
import main.space.Space;
import main.space.Row;
import main.space.Col;

public class StoneScorer extends Scorer {

    public StoneScorer(Building building) {
        super(building);
    }

    @Override
    public MaterialScore score() {
        if (!getBuilding().isValid()) {
            return new MaterialScore(Material.STONE, 0);
        }

        int totalScore = 0;

        for (int row = 1; row <= Building.MAX_ROWS; row++) {
            for (int col = 1; col <= Building.MAX_COLS; col++) {
                Space space = Space.from(Row.at(row), Col.at(col));
                DiceStack stack = getBuilding().getStack(space);
                int height = stack.getHeight();

                for (int level = 1; level <= height; level++) {
                    Die die = stack.getDie(level);
                    if (die.getMaterial() == Material.STONE) {
                        totalScore += getStoneScore(level);
                    }
                }
            }
        }

        return new MaterialScore(Material.STONE, totalScore);
    }

    private int getStoneScore(int level) {
        if (level == 1)
            return 2;
        if (level == 2)
            return 3;
        if (level == 3)
            return 5;
        return 8;
    }
}
