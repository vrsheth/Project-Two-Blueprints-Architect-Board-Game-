package main.gamedatasources;

import main.blueprint.Blueprint;
import main.building.Building;
import main.building.Die;
import main.exceptions.checked.GameDataSourceException;
import main.exceptions.checked.InvalidBuildingException;
import main.space.Col;
import main.space.Row;
import main.space.Space;

/**
 * This is meant to be an example of a class that implements the
 * ScoringDataSource interface.
 * 
 * It stubs out the two interface methods with hard-coded results.
 */
public class StubbedGameDataSource implements GameDataSource {

    @Override
    public Blueprint getBlueprint() throws GameDataSourceException {
        return new Blueprint("XX 33 XX");

    }

    @Override
    public Building getBuilding() throws GameDataSourceException {
        Building building = new Building();

        try {
            building.add(new Die("R2"), Space.from(Row.at(2), Col.at(1)));
            building.add(new Die("G4"), Space.from(Row.at(2), Col.at(1)));
            building.add(new Die("W5"), Space.from(Row.at(2), Col.at(1)));

            building.add(new Die("G1"), Space.from(Row.at(2), Col.at(2)));
            building.add(new Die("W4"), Space.from(Row.at(2), Col.at(2)));
            building.add(new Die("S6"), Space.from(Row.at(2), Col.at(2)));
            return building;
        } catch (InvalidBuildingException ex) {
            throw new GameDataSourceException("Game data source contains invalid building." + ex.getMessage());
        }

    }
}
