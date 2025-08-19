package main.building;

import main.exceptions.checked.InvalidBuildingException;
import main.logging.SimpleLogger;
import main.space.Col;
import main.space.Row;
import main.space.Space;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BuildingFileReader {

    private BuildingFileReader() {
        throw new UnsupportedOperationException("Building cannot be initialized");
    }

    public static Building load(String filePath, SimpleLogger logger)
            throws FileNotFoundException, InvalidBuildingException {
        StringBuilder content = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find building file: " + filePath);
        }

        return parseBuilding(content.toString(), logger);
    }

    private static Building parseBuilding(String data, SimpleLogger logger)
            throws InvalidBuildingException {
        if (data == null || data.trim().isEmpty()) {
            throw new InvalidBuildingException("Building data cannot be empty");
        }

        Building building = new Building();
        String[] lines = data.split("\n");

        Row currentRow = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            if (line.startsWith("[row.") && line.endsWith("]")) {
                try {
                    int rowNum = Integer.parseInt(line.substring(5, line.length() - 1));
                    currentRow = Row.at(rowNum);
                } catch (NumberFormatException e) {
                    currentRow = null;
                }
                continue;
            }

            if (currentRow == null)
                continue;

            if (line.startsWith("col")) {
                int equalIndex = line.indexOf('=');
                if (equalIndex == -1)
                    continue;

                String colPart = line.substring(4, equalIndex).trim();
                String diceData = line.substring(equalIndex + 1).trim().replace("\"", "");

                int colNum;
                try {
                    colNum = Integer.parseInt(colPart);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (colNum < 1 || colNum > Building.MAX_COLS)
                    continue;

                Col col = Col.at(colNum);
                Space space = Space.from(currentRow, col);

                String[] diceArray = diceData.split(" ");
                for (int i = 0; i < diceArray.length; i++) {
                    String dieStr = diceArray[i].trim();
                    if (!dieStr.isEmpty()) {
                        Die die = new Die(dieStr);
                        try {
                            building.add(die, space);
                        } catch (InvalidBuildingException e) {
                            int level = i + 1;
                            logger.log(String.format(
                                    "Error when adding %s at [%d,%d] to level %d. Building has these violations: %s.",
                                    dieStr,
                                    currentRow.getVal() + 1,
                                    col.getVal() + 1,
                                    level,
                                    building.getViolations()));
                        }
                    }
                }
            }
        }
        if (!building.isValid()) {
            throw new InvalidBuildingException("Building has violations.");
        }

        return building;
    }
}