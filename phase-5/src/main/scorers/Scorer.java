package main.scorers;

import java.util.ArrayList;
import main.building.Building;
import main.building.Material;
import main.building.Die;

public abstract class Scorer {

    protected final Building building;

    public Scorer(Building building) {
        this.building = building;
    }

    public ArrayList<Die> allIn(Material material) {
        return building.all(material);
    }

    public int countIn(Material material) {
        return building.all(material).size();
    }

    public ArrayList<Die> allOnLevel(int level, Material material) {
        return building.allOnLevel(material, level);
    }

    public ArrayList<Die> allOnLevel(int level) {
        ArrayList<Die> all = new ArrayList<>();
        for (Material m : Material.values()) {
            all.addAll(building.allOnLevel(m, level));
        }
        return all;
    }

    public ArrayList<Die> allAdjacentTo(Material material) {
        return building.allAdjacentTo(material);
    }

    public Building getBuilding() {
        return building;
    }

    public abstract MaterialScore score();
}
