package main.gamedatasources;

import main.blueprint.Blueprint;
import main.blueprint.BlueprintFileReader;
import main.building.Building;
import main.building.BuildingFileReader;
import main.exceptions.checked.GameDataSourceException;
import main.exceptions.checked.InvalidBuildingException;
import main.logging.SimpleLogger;

import java.io.File;
import java.io.FileNotFoundException;

public class TextFileGameDataSource implements GameDataSource {

    private final File blueprintFile;
    private final File buildingFile;
    private final SimpleLogger logger;

    public TextFileGameDataSource(File blueprintFile, File buildingFile, SimpleLogger logger) {
        this.blueprintFile = blueprintFile;
        this.buildingFile = buildingFile;
        this.logger = logger;
    }

    public TextFileGameDataSource(String blueprintFilePath, String buildingFilePath, SimpleLogger logger) {
        this(new File(blueprintFilePath), new File(buildingFilePath), logger);
    }

    public TextFileGameDataSource(String blueprintFilePath, String buildingFilePath) {
        this(new File(blueprintFilePath), new File(buildingFilePath), new SimpleLogger());
    }

    @Override
    public Blueprint getBlueprint() {
        try {
            return BlueprintFileReader.load(blueprintFile.getAbsolutePath());
        } catch (Exception e) {
            throw new GameDataSourceException("Failed to read blueprint file: " + blueprintFile.getName(), e);
        }
    }

    @Override
    public Building getBuilding() {
        try {
            return BuildingFileReader.load(buildingFile.getAbsolutePath(), logger);
        } catch (FileNotFoundException | InvalidBuildingException e) {
            throw new GameDataSourceException("Failed to read building file: " + buildingFile.getName(), e);
        }
    }
}
