package main.scorers;

import main.building.Building;
import main.building.Die;
import main.building.Material;

import java.util.ArrayList;

public class CustomScorer extends Scorer {

    public CustomScorer(Building building) {
        super(building);
    }

    @Override
    public MaterialScore score() {
        if (!getBuilding().isValid()) {
            return new MaterialScore(Material.GLASS, 0);
        }

        ArrayList<Die> glassDice = allIn(Material.GLASS);
        if (glassDice.isEmpty()) {
            return new MaterialScore(Material.GLASS, 0);
        }

        boolean allOdd = true;
        boolean allEven = true;
        int total = 0;

        for (Die die : glassDice) {
            int face = die.getFace();
            total += face;
            if (face % 2 == 0) {
                allOdd = false;
            } else {
                allEven = false;
            }
        }

        if (allOdd || allEven) {
            return new MaterialScore(Material.GLASS, total);
        } else {
            return new MaterialScore(Material.GLASS, 0);
        }
    }
}
