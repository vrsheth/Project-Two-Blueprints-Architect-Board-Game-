package main.scorers;

import main.building.Building;
import main.building.Material;

public class RecycledScorer extends Scorer {

    public RecycledScorer(Building building) {
        super(building);
    }

    @Override
    public MaterialScore score() {
        if (!getBuilding().isValid()) {
            return new MaterialScore(Material.RECYCLED, 0);
        }

        int count = countIn(Material.RECYCLED);
        int score;

        switch (count) {
            case 1:
                score = 2;
                break;
            case 2:
                score = 5;
                break;
            case 3:
                score = 10;
                break;
            case 4:
                score = 15;
                break;
            case 5:
                score = 20;
                break;
            case 6:
                score = 30;
                break;
            default:
                score = 0;
        }

        return new MaterialScore(Material.RECYCLED, score);
    }
}
