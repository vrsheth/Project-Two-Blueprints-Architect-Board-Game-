package test.mytests;

import main.building.Building;
import main.building.Die;
import main.building.Material;
import main.scorers.CustomScorer;
import main.scorers.MaterialScore;
import main.space.Col;
import main.space.Row;
import main.space.Space;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomScorerTest {

    @Test
    void testAllOddGlassDice() throws Exception {
        Building building = new Building();
        building.add(new Die(Material.GLASS, 1), Space.from(Row.at(1), Col.at(1)));
        building.add(new Die(Material.GLASS, 3), Space.from(Row.at(1), Col.at(2)));
        building.add(new Die(Material.GLASS, 5), Space.from(Row.at(2), Col.at(1)));

        CustomScorer scorer = new CustomScorer(building);
        MaterialScore score = scorer.score();

        assertEquals(Material.GLASS, score.getMaterial());
        assertEquals(9, score.getScore());
    }

    @Test
    void testAllEvenGlassDice() throws Exception {
        Building building = new Building();
        building.add(new Die(Material.GLASS, 2), Space.from(Row.at(1), Col.at(1)));
        building.add(new Die(Material.GLASS, 4), Space.from(Row.at(1), Col.at(2)));
        building.add(new Die(Material.GLASS, 6), Space.from(Row.at(2), Col.at(1)));

        CustomScorer scorer = new CustomScorer(building);
        MaterialScore score = scorer.score();

        assertEquals(Material.GLASS, score.getMaterial());
        assertEquals(12, score.getScore());
    }

    @Test
    void testMixedOddEvenGlassDice() throws Exception {
        Building building = new Building();
        building.add(new Die(Material.GLASS, 1), Space.from(Row.at(1), Col.at(1)));
        building.add(new Die(Material.GLASS, 2), Space.from(Row.at(1), Col.at(2)));
        building.add(new Die(Material.GLASS, 3), Space.from(Row.at(2), Col.at(1)));

        CustomScorer scorer = new CustomScorer(building);
        MaterialScore score = scorer.score();

        assertEquals(Material.GLASS, score.getMaterial());
        assertEquals(0, score.getScore());
    }

    @Test
    void testNoGlassDice() {
        Building building = new Building(); // Empty building

        CustomScorer scorer = new CustomScorer(building);
        MaterialScore score = scorer.score();

        assertEquals(Material.GLASS, score.getMaterial());
        assertEquals(0, score.getScore());
    }

    @Test
    void testMixedMaterialsOnlyGlassEvaluated() throws Exception {
        Building building = new Building();
        building.add(new Die(Material.GLASS, 2), Space.from(Row.at(1), Col.at(1)));
        building.add(new Die(Material.RECYCLED, 5), Space.from(Row.at(1), Col.at(2)));
        building.add(new Die(Material.STONE, 3), Space.from(Row.at(2), Col.at(1)));

        CustomScorer scorer = new CustomScorer(building);
        MaterialScore score = scorer.score();

        assertEquals(Material.GLASS, score.getMaterial());
        assertEquals(2, score.getScore());
    }
}
