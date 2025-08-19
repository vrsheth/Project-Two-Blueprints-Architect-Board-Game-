package main.violations;

/**
 * All possible rules violations to detect during scoring.
 * <p>
 * The violations are:
 * <ul>
 * <li>DESCENDING_DICE - at least one die has a lower face value than the die
 * underneath
 * <li>BUILDING_OVERLARGE - building containing more than 6 dice detected
 * <li>STACK_OVERLARGE - stack of dice containing more than 6 dice detected
 * <li>INVALID_PLACEMENT - stack of dice placed on forbidden blueprint space
 * detected
 * <p>
 * Note that although a building with an overlarge stack is guaranteed to be
 * itself overlarge, a building that is overlarge is NOT guaranteed to have an
 * overlarge stack!
 */
public enum RuleViolation {
    DESCENDING_DICE,
    BUILDING_OVERLARGE,
    STACK_OVERLARGE,
    INVALID_PLACEMENT
}
