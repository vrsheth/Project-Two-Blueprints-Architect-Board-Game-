package test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.Main;

public class RoundTripTests {

    @Test
    public void StubbedGameDataSource_to_StubbedScoringResultPresenter() {
        // Running main() with no args will cause stubbed GameDataSource and Presenters
        // to be used.
        String[] args = {};
        try {
            Main.main(args);
        } catch (Exception ex) {
            fail("This test should not throw any exceptions!");
        }

    }

    @Test
    public void TextFileGameDataSource_to_StubbedScoringResultPresenter() {
        String[] args = {
                "--file=res/marking/file-to-stub/blueprint.txt,res/marking/file-to-stub/building.txt",
                "--stubbed" };

        try {
            Main.main(args);
        } catch (Exception ex) {
            fail("This test should not throw any exceptions!");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "01", "02", "03", "04", "05", "06", "07" })
    public void TextFileGameDataSource_to_TextFileScoringResultPresenter_no_exceptions(String testNum)
            throws IOException {
        String dataFileArgs = String.format(
                "--file=res/marking/file-to-file/blueprint-%s.txt,res/marking/file-to-file/building-%s.txt", testNum,
                testNum);
        String resultFileArgs = String.format("--file=res/marking/file-to-file/scoring-result-%s.txt", testNum);

        String[] args = { dataFileArgs, resultFileArgs };
        Main.main(args);

        List<String> expectedLines = expectedFileLines("file-to-file", testNum);
        List<String> actualLines = actualFileLines("file-to-file", testNum);

        assertIterableEquals(expectedLines, actualLines);
    }

    private String expectedResultPath(String prefix, String testNum) {
        return String.format("res/marking/%s/expected-scoring-result-%s.txt", prefix, testNum);
    }

    private String actualResultPath(String prefix, String testNum) {
        return String.format("res/marking/%s/scoring-result-%s.txt", prefix, testNum);
    }

    private List<String> expectedFileLines(String prefix, String testNum) throws IOException {
        Path expectedResultPath = Paths.get(expectedResultPath(prefix, testNum));
        return Files.readAllLines(expectedResultPath);
    }

    private List<String> actualFileLines(String prefix, String testNum) throws IOException {
        Path actualResultPath = Paths.get(actualResultPath(prefix, testNum));
        return Files.readAllLines(actualResultPath);
    }

}
