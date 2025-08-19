package main.building;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import main.space.Col;
import main.space.Row;
import main.space.Space;

public class BuildingFileReader {

    private BuildingFileReader() {
        throw new UnsupportedOperationException("Building cannot be initialized");
    }

    public static Building load(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            throw new Exception("Error reading building file: " + filePath, e);
        }

        return parseBuilding(content.toString());
    }

    private static Building parseBuilding(String data) {
        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("Building data cannot be empty");
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
                    currentRow = new Row(rowNum);
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

                Col col = new Col(colNum);
                Space space = Space.from(currentRow, col);
                DiceStack stack = building.getStack(space);

                String[] diceArray = diceData.split(" ");
                for (String dieStr : diceArray) {
                    if (!dieStr.isEmpty()) {
                        stack.add(new Die(dieStr.trim()));
                    }
                }

                building.add(stack, space);
            }
        }

        return building;
    }
}
