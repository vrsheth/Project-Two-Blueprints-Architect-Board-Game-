package main.blueprint;

import main.space.Space;
import main.space.Row;
import main.space.Col;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for reading and loading a {@link Blueprint} from a file.
 */
public class BlueprintFileReader {

    /**
     * Private constructor to prevent instantiation.
     * Throws an {@link UnsupportedOperationException} if called.
     */
    private BlueprintFileReader() {
        throw new UnsupportedOperationException("BlueprintFileReader cannot be instantiated.");
    }

    /**
     * Loads a blueprint from a specified file.
     *
     * @param filePath the path to the blueprint file
     * @return a {@link Blueprint} object created from the file contents
     * @throws Exception if an error occurs while reading the file
     */
    public static Blueprint load(String filePath) throws Exception {
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new Exception("Error reading blueprint file: " + filePath, e);
        }

        return parseBlueprint(lines);
    }

    /**
     * Parses the blueprint data from a list of strings.
     * Assumes the grid is 3 rows x 2 columns and validates using Row/Col.
     *
     * @param lines the lines of the blueprint file
     * @return a {@link Blueprint} object
     * @throws Exception if any error occurs during parsing
     */
    private static Blueprint parseBlueprint(List<String> lines) throws Exception {
        StringBuilder blueprintString = new StringBuilder();

        for (int rowIndex = 0; rowIndex < lines.size(); rowIndex++) {
            String line = lines.get(rowIndex);

            for (int colIndex = 0; colIndex < line.length(); colIndex++) {
                int rowVal = rowIndex + 1;
                int colVal = colIndex + 1;

                try {
                    Row row = Row.at(rowVal);
                    Col col = Col.at(colVal);
                    @SuppressWarnings("unused")
                    Space space = Space.from(row, col);

                    // Optionally print or log space and content
                    // System.out.println("Space: " + space + " | Char: " + line.charAt(colIndex));
                } catch (Exception e) {
                    throw new Exception("Invalid row/col at (" + rowVal + ", " + colVal + "): " + e.getMessage());
                }

                blueprintString.append(line.charAt(colIndex));
            }

            blueprintString.append("\n");
        }

        return new Blueprint(blueprintString.toString());
    }
}
