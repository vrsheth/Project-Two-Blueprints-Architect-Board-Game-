package main.scorers;

import main.building.Material;

public class MaterialScore {

    private final Material material;
    private final int score;

    public MaterialScore(Material material, int score) {
        this.material = material;
        this.score = score;
    }

    public Material getMaterial() {
        return material;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return material.toString().toLowerCase() + ": " + score;
    }
}
