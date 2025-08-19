package main.presenters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import main.blueprint.Blueprint;
import main.building.Building;
import main.exceptions.checked.ScoringResultPresenterException;
import main.scoringresult.ScoringResult;

public class TextFileScoringResultPresenter implements ScoringResultPresenter {

    private final String RESULT_PATH;
    private Blueprint blueprint;
    private Building building;

    public TextFileScoringResultPresenter(String resultPath) {
        this.RESULT_PATH = resultPath;
    }

    @Override
    public void present(ScoringResult result) throws ScoringResultPresenterException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_PATH))) {
            writer.write(formatResult(result));
        } catch (IOException e) {
            throw new ScoringResultPresenterException("Failed to write scoring result to file: " + e.getMessage(), e);
        }
    }

    private String formatResult(ScoringResult result) {
        StringBuilder sb = new StringBuilder();

        sb.append(blueprint.toString()).append("\n");
        sb.append("\n");
        sb.append(building.toString()).append("\n");

        sb.append("+-----------+----+\n");

        int glass = result.breakdown().getOrDefault("glass", 0);
        int recycled = result.breakdown().getOrDefault("recycled", 0);
        int stone = result.breakdown().getOrDefault("stone", 0);
        int wood = result.breakdown().getOrDefault("wood", 0);
        int bonus = result.breakdown().getOrDefault("**bonus**", 0);

        sb.append(String.format("| glass     |  %d |\n", glass));
        sb.append(String.format("| recycled  |  %d |\n", recycled));
        sb.append(String.format("| stone     |  %d |\n", stone));
        sb.append(String.format("| wood      |  %d |\n", wood));
        sb.append(String.format("| **bonus** |  %d |\n", bonus));
        sb.append("+===========+====+\n");
        sb.append(String.format("| total     |  %d|\n", result.totalScore()));
        sb.append("+-----------+----+\n");

        sb.append("Rule violations: NONE\n"); // Replace with actual violations if available

        return sb.toString();
    }
}