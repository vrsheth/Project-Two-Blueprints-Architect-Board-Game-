package main.gamedatasources;

import main.blueprint.Blueprint;
import main.building.Building;
import main.exceptions.checked.GameDataSourceException;

public interface GameDataSource {
    Blueprint getBlueprint() throws GameDataSourceException;

    Building getBuilding() throws GameDataSourceException;
}
