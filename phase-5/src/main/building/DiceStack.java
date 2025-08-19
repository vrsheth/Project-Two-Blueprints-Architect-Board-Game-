package main.building;

import main.space.Space;
import main.space.Row;
import main.exceptions.runtime.InvalidLevelException;
import main.space.Col;
import main.violations.RuleViolation;
import main.violations.ViolationList;

import java.util.ArrayList;

/**
 * Represents a stack of dice at a specific space with validation rules.
 */
public class DiceStack {
    private final Space space;
    private ArrayList<Die> dice;
    private int height;
    private ViolationList violations;

    /**
     * Default constructor that creates a DiceStack at Space(Row 1, Col 1).
     */
    public DiceStack() {
        this(Space.from(Row.at(1), Col.at(1)));
    }

    /**
     * Constructs an empty DiceStack at the given space.
     *
     * @param space The location of the stack.
     * @throws IllegalArgumentException if space is null.
     */
    public DiceStack(Space space) {
        if (space == null) {
            throw new IllegalArgumentException("Space cannot be null");
        }
        this.space = space;
        this.dice = new ArrayList<>();
        this.height = 0;
        this.violations = new ViolationList();
    }

    /**
     * Copy constructor that creates a new DiceStack as a deep copy of another.
     *
     * @param other The DiceStack to copy.
     */
    public DiceStack(DiceStack other) {
        this.space = other.space; // Space is immutable, safe to reuse
        this.dice = new ArrayList<>(other.dice);
        this.height = other.height;
        this.violations = new ViolationList(other.violations);
    }

    /**
     * Adds a die to the stack, enforcing validation rules.
     *
     * @param die The die to add.
     * @throws IllegalArgumentException If the die is null.
     */
    public void add(Die die) {
        if (die == null) {
            throw new IllegalArgumentException("Die cannot be null");
        }
        if (!dice.isEmpty() && die.getFace() < dice.get(height - 1).getFace()) {
            violations.add(RuleViolation.DESCENDING_DICE);
        }
        if (height >= 6) {
            violations.add(RuleViolation.STACK_OVERLARGE);
        }
        dice.add(die);
        height++;
    }

    public boolean isEmpty() {
        return dice.isEmpty();
    }

    public boolean isValid() {
        return !violations.hasViolations();
    }

    public Die getDie(int level) {
        if (level < 1 || level > dice.size()) {
            throw new InvalidLevelException("No Die present at level " + level + ".");

        }
        return dice.get(level - 1);
    }

    public ArrayList<Die> getDice() {
        return new ArrayList<>(dice);
    }

    public int getHeight() {
        return height;
    }

    public ViolationList getViolations() {
        return new ViolationList(violations);
    }

    public Space getSpace() {
        return space;
    }

    @Override
    public String toString() {
        if (dice.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < dice.size(); i++) {
            sb.append(dice.get(i).toString());
            if (i < dice.size() - 1) {
                sb.append(" ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
