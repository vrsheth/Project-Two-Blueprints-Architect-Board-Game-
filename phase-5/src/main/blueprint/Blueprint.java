package main.blueprint;

import main.building.Building;
import main.space.*;
import main.exceptions.runtime.InvalidBlueprintTemplateException;

public class Blueprint {
    private final String spaces;
    private static final int WIDTH = 2;
    private static final int HEIGHT = 3;

    /**
     * Constructs a Blueprint object with a given input string.
     * Removes all whitespace characters from the input.
     * Validates the input and throws an exception if it's invalid.
     *
     * @param input the input string representing the blueprint
     * @throws InvalidBlueprintTemplateException if the input is not valid
     */
    public Blueprint(String input) {
        String cleaned = input.replaceAll("\\s", "");

        // Valid characters are only 1, 2, 3, X, and 0
        if (cleaned.length() != WIDTH * HEIGHT || !cleaned.matches("[123X]{" + (WIDTH * HEIGHT) + "}")) {
            throw new InvalidBlueprintTemplateException("Invalid blueprint template used.");
        }

        this.spaces = cleaned;
    }

    public boolean isOpenSpace(Space space) {
        char c = getCharAt(space);
        return c == '1' || c == '2' || c == '3';
    }

    public boolean isProhibitedSpace(Space space) {
        return getCharAt(space) == 'X';
    }

    public int heightTargetAt(Space space) {
        char c = getCharAt(space);
        return (c >= '1' && c <= '3') ? Character.getNumericValue(c) : 0;
    }

    private char getCharAt(Space space) {
        int row = space.rowVal();
        int col = space.colVal();
        int index = row * WIDTH + col;
        return (index >= 0 && index < spaces.length()) ? spaces.charAt(index) : ' ';
    }

    public boolean isFollowedBy(Building building) {
        for (int row = 1; row <= HEIGHT; row++) {
            for (int col = 1; col <= WIDTH; col++) {
                Row r = Row.at(row);
                Col c = Col.at(col);
                Space space = Space.from(r, c);

                int requiredHeight = heightTargetAt(space);
                if (requiredHeight > 0) {
                    if (building.getStack(space).getHeight() != requiredHeight) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < HEIGHT; i++) {
            sb.append(spaces, i * WIDTH, Math.min((i + 1) * WIDTH, spaces.length()));
            if (i < HEIGHT - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
