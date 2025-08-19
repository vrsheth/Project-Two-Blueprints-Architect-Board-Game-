package main.scoringresult;

import main.blueprint.Blueprint;
import main.building.Building;
import main.building.DiceStack;
import main.exceptions.checked.InvalidPlacementException;
import main.space.Col;
import main.space.Row;
import main.space.Space;

public class ScoringResult {

    public ScoringResult(Blueprint blueprint, Building building) throws InvalidPlacementException {
        for (int row = 1; row <= Building.MAX_ROWS; row++) {
            for (int col = 1; col <= Building.MAX_COLS; col++) {
                Space space = Space.from(Row.at(row), Col.at(col));
                DiceStack stack = building.getStack(space);
                if (!stack.isEmpty() && blueprint.isProhibitedSpace(space)) {
                    throw new InvalidPlacementException("Invalid placement happened at " + space + ".");
                }
            }
        }
    }
}
