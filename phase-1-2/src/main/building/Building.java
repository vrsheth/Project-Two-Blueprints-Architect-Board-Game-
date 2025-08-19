package main.building;

import main.exceptions.runtime.InvalidLevelException;
import main.space.Col;
import main.space.Row;
import main.space.Space;
import main.violations.RuleViolation;
import main.violations.ViolationList;

import java.util.ArrayList;

/**
 * Represents a building made up of dice stacks.
 */
public class Building {
    private DiceStack[][] stacks;
    private int height;
    private boolean isValid;
    private int numDice;
    private ViolationList violations;
    public static final int MAX_ROWS = 3;
    public static final int MAX_COLS = 2;
    private static final String SEPARATOR = "==+==\n";

    public Building() {
        stacks = new DiceStack[MAX_ROWS][MAX_COLS];
        height = 0;
        numDice = 0;
        isValid = true;
        violations = new ViolationList();
    }

    public Building(Building other) {
        stacks = new DiceStack[MAX_ROWS][MAX_COLS];
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                if (other.stacks[i][j] != null) {
                    stacks[i][j] = new DiceStack(other.stacks[i][j]);
                }
            }
        }
        height = other.height;
        numDice = other.numDice;
        isValid = other.isValid;
        violations = new ViolationList(other.violations);
    }

    public void add(Die die, Space space) {
        int row = space.rowVal();
        int col = space.colVal();

        if (stacks[row][col] == null) {
            stacks[row][col] = new DiceStack();
        }
        stacks[row][col].add(die);
        numDice++;
        updateBuildingState();
    }

    public void add(DiceStack stack, Space space) {
        stacks[space.rowVal()][space.colVal()] = new DiceStack(stack);
        updateBuildingState();
    }

    public boolean isEmpty() {
        return numDice == 0;
    }

    public boolean isValid() {
        return isValid;
    }

    public Die getDie(Space space, int level) {
        int row = space.rowVal();
        int col = space.colVal();

        DiceStack stack = stacks[row][col];
        if (stack == null || level < 1 || level > stack.getHeight()) {
            throw new InvalidLevelException("No die at level " + level + " at [" + (row + 1) + "," + (col + 1) + "].");
        }

        return stack.getDie(level);
    }

    public DiceStack getStack(Space space) {
        DiceStack stack = stacks[space.rowVal()][space.colVal()];
        return (stack != null) ? new DiceStack(stack) : new DiceStack();
    }

    public int getNumDice() {
        return numDice;
    }

    public int getHeight() {
        return height;
    }

    public ViolationList getViolations() {
        return new ViolationList(violations);
    }

    public ArrayList<Die> all(Material material) {
        ArrayList<Die> result = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                DiceStack stack = stacks[i][j];
                if (stack != null) {
                    for (Die die : stack.getDice()) {
                        if (die.getMaterial() == material) {
                            result.add(die);
                        }
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<Die> allOnLevel(Material material, int level) {
        if (level < 1 || level > getHeight()) {
            throw new InvalidLevelException("Building has no die at level " + level + ".");
        }
        ArrayList<Die> result = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                DiceStack stack = stacks[i][j];
                if (stack != null) {
                    Die die = stack.getDie(level);
                    if (die != null && die.getMaterial() == material) {
                        result.add(die);
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<Die> allAdjacentTo(Material material) {
        ArrayList<Die> result = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                DiceStack stack = stacks[i][j];
                if (stack != null) {
                    for (Die die : stack.getDice()) {
                        Space space = Space.from(Row.at(i + 1), Col.at(j + 1));
                        if (isAdjacentTo(space, material)) {
                            result.add(die);
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean isAdjacentTo(Space space, Material material) {
        int[] dRow = { -1, 1, 0, 0 };
        int[] dCol = { 0, 0, -1, 1 };
        int r = space.rowVal();
        int c = space.colVal();

        for (int k = 0; k < 4; k++) {
            int newRow = r + dRow[k];
            int newCol = c + dCol[k];
            if (newRow >= 0 && newRow < MAX_ROWS && newCol >= 0 && newCol < MAX_COLS) {
                DiceStack adjStack = stacks[newRow][newCol];
                if (adjStack != null) {
                    for (Die adjDie : adjStack.getDice()) {
                        if (adjDie.getMaterial() == material) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void updateBuildingState() {
        violations = new ViolationList();
        numDice = 0;
        height = 0;

        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                DiceStack stack = stacks[i][j];
                if (stack != null) {
                    numDice += stack.getHeight();
                    height = Math.max(height, stack.getHeight());
                    violations.addAll(stack.getViolations());
                }
            }
        }

        if (numDice > 6) {
            violations.add(RuleViolation.BUILDING_OVERLARGE);
        }

        isValid = !violations.hasViolations();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "<< EMPTY BUILDING >>\n";
        }

        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < MAX_ROWS; row++) {
            for (int level = getHeight(); level >= 1; level--) {
                String left = formatSingleDiceColumn(stacks[row][0], level);
                String right = formatSingleDiceColumn(stacks[row][1], level);
                sb.append(left).append("|").append(right).append("\n");
            }
            if (row < MAX_ROWS - 1) {
                sb.append(SEPARATOR);
            }
        }

        return sb.toString();
    }

    private String formatSingleDiceColumn(DiceStack stack, int level) {
        if (stack == null || stack.isEmpty()) {
            return "--";
        }
        Die die = stack.getDie(level);
        return (die != null) ? die.toString() : "--";
    }
}