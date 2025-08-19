package main.violations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;

/**
 * Represents a list of {@link RuleViolation}.
 * <p>
 * Since there can only be one type of violation present in the list, adding
 * multiple violations to the list doesn't create duplicates.
 */
public class ViolationList {
    private EnumSet<RuleViolation> violations;

    /**
     * Construct an empty list.
     */
    public ViolationList() {
        violations = EnumSet.noneOf(RuleViolation.class);
    }

    /**
     * Construct a copy of another provided list.
     * 
     * @param otherList the list to construct a copy of
     */
    public ViolationList(ViolationList otherViolationList) {
        violations = otherViolationList.violations.clone();
    }

    /**
     * Returns true if any violations are present in this list.
     * 
     * @returns true if any violations are present in this list
     */
    public boolean hasViolations() {
        return !violations.isEmpty();
    }

    /**
     * Adds a violation to this list, if the violation is not already present.
     * 
     * @param violation the violation to add
     */
    public void add(RuleViolation violation) {
        violations.add(violation);
    }

    /**
     * Adds all violations from a provided list into this list. Duplicate violations
     * are ignored.
     * 
     * @param otherViolationList the other list of violations to add to this list
     */
    public void addAll(ViolationList otherViolationList) {
        violations.addAll(otherViolationList.violations);
    }

    @Override
    /**
     * Returns either NONE or an alphabetically-ordered list of rules violations.
     * 
     * @returns a properly-formatted list representation
     */
    public String toString() {
        if (violations.isEmpty()) {
            return "NONE";
        }

        ArrayList<String> msgs = new ArrayList<>();
        for (RuleViolation violation : violations) {
            msgs.add(violation.toString());
        }
        Collections.sort(msgs);

        return msgs.toString();
    }
}
