package main.scorers;

import main.building.Building;
import main.building.Die;
import main.building.Material;

import java.util.ArrayList;

public class GlassScorer extends Scorer {

    public GlassScorer(Building building) {
        super(building);
    }

    @Override
    public MaterialScore score() {
        if (!getBuilding().isValid()) {
            return new MaterialScore(Material.GLASS, 0);
        }

        ArrayList<Die> glassDice = allIn(Material.GLASS);
        int total = 0;
        for (Die die : glassDice) {
            total += die.getFace();
        }

        return new MaterialScore(Material.GLASS, total);
    }
}
