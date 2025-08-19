package main;

import main.blueprint.Blueprint;
import main.building.Building;
import main.exceptions.checked.GameDataSourceException;
import main.gamedatasources.GameDataSource;
import main.logging.Loggable;
import main.presenters.ScoringResultPresenter;
import main.scoringresult.ScoringResult;

public class BlueprintsScoringApp {

    private final GameDataSource gameData;
    private final ScoringResultPresenter presenter;
    private final Loggable logger;

    public BlueprintsScoringApp(GameDataSource gameData, ScoringResultPresenter presenter, Loggable logger) {
        this.gameData = gameData;
        this.presenter = presenter;
        this.logger = logger;
    }

    public void run() {

        // Your code goes here.
        try {
            Blueprint blueprint = gameData.getBlueprint();
            Building building = gameData.getBuilding();
            ScoringResult result = new ScoringResult(blueprint, building, 0, null);
            presenter.present(result);
        } catch (GameDataSourceException e) {
            logger.log(e.getMessage());
            System.out.println("Error happened while attempting to get game data; consult log for details.");
        } catch (Exception e) {
            logger.log(e.getMessage());
            System.out.println("Unexpected error occurred; consult log for details.");
        }

    }

}
