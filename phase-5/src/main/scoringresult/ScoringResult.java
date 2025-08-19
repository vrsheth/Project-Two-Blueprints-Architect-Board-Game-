package main.scoringresult;

import java.util.Map;

import main.blueprint.Blueprint;
import main.building.Building;
import main.building.DiceStack;
import main.exceptions.checked.InvalidPlacementException;
import main.space.Col;
import main.space.Row;
import main.space.Space;

public class ScoringResult {

    private final int totalScore;
    private final Map<String, Integer> scoreBreakdown;

    public ScoringResult(Blueprint blueprint, Building building, int totalScore, Map<String, Integer> breakdown)
            throws InvalidPlacementException {
        this.totalScore = totalScore;
        this.scoreBreakdown = breakdown;

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

    public int totalScore() {
        return totalScore;
    }

    public Map<String, Integer> breakdown() {
        return scoreBreakdown;
    }
}
