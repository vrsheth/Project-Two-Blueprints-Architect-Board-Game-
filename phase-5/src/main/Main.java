package main;

import main.gamedatasources.GameDataSource;
import main.gamedatasources.StubbedGameDataSource;
import main.gamedatasources.TextFileGameDataSource;
import main.logging.Loggable;
import main.logging.SimpleLogger;
import main.presenters.ScoringResultPresenter;
import main.presenters.StubbedScoringResultPresenter;
import main.presenters.TextFileScoringResultPresenter;
//import main.presenters.WebScoringResultPresenter;

/**
 * Driver for the completed application.
 * 
 * By running the driver with various flags, where the scoring data
 * comes from and how the results are presented can be changed.
 * 
 * If the first flag is --file=path/to/blueprint,path/to/building, then
 * scoring data will be grabbed from the specified blueprint and
 * building files. Otherwise, a stubbed data source is used. (I had
 * intended to allow data to come from an online source that served
 * up JSON, but life got in the way of THAT little plan.)
 * 
 * If the second flag is --file=path/to/results, then the results
 * are dumped to the specified file, using the exact same format as
 * from Assignment 2. If the second flag is --web, then the scoring
 * results are displayed as a web page and a 3D model of the building
 * being scored is created as well. Otherwise, a stubbed presenter
 * is used to output a really useless message to the console.
 */
public class Main {

    public static void main(String[] args) {
        String dataSourceArgs = "";
        String presenterArgs = "";

        if (args.length == 2) {
            dataSourceArgs = args[0];
            presenterArgs = args[1];
        }

        Loggable logger = new SimpleLogger();
        GameDataSource source = dataSourceFromArgs(dataSourceArgs);
        ScoringResultPresenter presenter = presenterFromArgs(presenterArgs);

        BlueprintsScoringApp app = new BlueprintsScoringApp(source, presenter, logger);
        app.run();

    }

    private static GameDataSource dataSourceFromArgs(String dataSourceArgs) {
        if (dataSourceArgs.startsWith("--file=")) {
            String[] splitArg = dataSourceArgs.split("=");
            String[] filePaths = splitArg[1].split(",");
            String blueprintPath = filePaths[0];
            String buildingPath = filePaths[1];
            return new TextFileGameDataSource(blueprintPath, buildingPath);
        } else {
            return new StubbedGameDataSource();
        }
    }

    private static ScoringResultPresenter presenterFromArgs(String presenterArgs) {
        if (presenterArgs.startsWith("--file=")) {
            String[] splitArg = presenterArgs.split("=");
            String resultPath = splitArg[1];
            return new TextFileScoringResultPresenter(resultPath);
        } else if (presenterArgs.equals("--web")) {
            // return new WebScoringResultPresenter();
        } else {
            return new StubbedScoringResultPresenter();
        }
    }
}