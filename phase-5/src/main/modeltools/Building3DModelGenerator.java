package main.modeltools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import main.building.Building;
import main.building.DiceStack;
import main.building.Die;
import main.building.Material;
import main.space.Col;
import main.space.Row;
import main.space.Space;

/**
 * Provides a way of saving a building as an 3D model in OBJ format. (See
 * https://paulbourke.net/dataformats/obj/)
 * 
 * Dice in the model don't show any pips (I didn't have time to play) and are
 * resting on a blue plane (I call it "base" here) that represents a blueprint
 * card.
 * 
 * If you want to view the model, you'll need to go to the directory you
 * specify in the save3DBuilding() method call, find the building3d.zip file,
 * and drag and drop that sucker into https://3dviewer.net/ (or use the zip in
 * any software that can read a zip bundle of obj and mtl files).
 */
public class Building3DModelGenerator {

    private static final double[][] UNIT_CUBE = {
            { 0.0, 0.01, 0.0 },
            { 1.0, 0.01, 0.0 },
            { 1.0, 1.01, 0.0 },
            { 0.0, 1.01, 0.0 },
            { 0.0, 0.01, 1.0 },
            { 1.0, 0.01, 1.0 },
            { 1.0, 1.01, 1.0 },
            { 0.0, 1.01, 1.0 }
    };

    private static final int[][] UNIT_CUBE_FACES = {
            { 5, 6, 7, 8 },
            { 6, 10, 11, 7 },
            { 10, 9, 12, 11 },
            { 9, 5, 8, 12 },
            { 8, 7, 11, 12 },
            { 9, 10, 6, 5 }
    };

    private final Building building;

    /**
     * This is just a rough example of how you can use this class
     * and its sole public method, save3DBuilding().
     */
    public static void main(String[] args) throws Exception {

        // Rough example of how you can use this class.

        Building b = new Building();
        b.add(new Die("G1"), Space.from(Row.at(1), Col.at(2)));
        b.add(new Die("W2"), Space.from(Row.at(1), Col.at(2)));
        // b.add(new Die("W1"), Space.from(Row.at(1), Col.at(1)));
        // b.add(new Die("W2"), Space.from(Row.at(1), Col.at(1)));
        // b.add(new Die("G3"), Space.from(Row.at(1), Col.at(1)));

        // b.add(new Die("S2"), Space.from(Row.at(3), Col.at(2)));
        // b.add(new Die("R2"), Space.from(Row.at(3), Col.at(2)));

        // b.add(new Die("W2"), Space.from(Row.at(2), Col.at(1)));

        Building3DModelGenerator generator = new Building3DModelGenerator(b);

        // This is a DIRECTORY where the necessary files will be placed.
        generator.save3DBuilding("target");

    }

    public void save3DBuilding(String pathDir) throws FileNotFoundException, IOException {
        saveMaterials(pathDir);
        saveModel(pathDir);
        zipModel(pathDir);
        deleteTmpFiles(pathDir);
    }

    private void saveMaterials(String pathDir) throws FileNotFoundException {
        saveMaterial("card", pathDir, cardMtlFileContents());
        saveMaterial("glass", pathDir, glassMtlFileContents());
        saveMaterial("recy", pathDir, recyMtlFileContents());
        saveMaterial("stone", pathDir, stoneMtlFileContents());
        saveMaterial("wood", pathDir, woodMtlFileContents());
    }

    private void saveModel(String modelDirPath) throws FileNotFoundException, IOException {
        PrintWriter writer = new PrintWriter(modelDirPath + "/cubey.obj");
        writer.println(libraries());
        writer.println(baseCard());

        int dieNum = 0;

        for (int rowVal = 1; rowVal <= 3; rowVal++) {
            for (int colVal = 1; colVal <= 2; colVal++) {
                Space currSpace = Space.from(Row.at(rowVal), Col.at(colVal));
                DiceStack currStack = building.getStack(currSpace);
                int numDiceInStack = currStack.getHeight();

                for (int level = 1; level <= numDiceInStack; level++) {
                    Die die = currStack.getDie(level);
                    dieNum++;
                    writer.println(oneCube(die, currSpace, level, dieNum));
                }
            }
        }

        writer.close();

    }

    private void zipModel(String zipRootDirPath) throws FileNotFoundException, IOException {
        String cardMtlFile = zipRootDirPath + "/card.mtl";
        String glassMtlFile = zipRootDirPath + "/glass.mtl";
        String recyledMtlFile = zipRootDirPath + "/recy.mtl";
        String stoneMtlFile = zipRootDirPath + "/stone.mtl";
        String woodMtlFile = zipRootDirPath + "/wood.mtl";
        String cubeFile = zipRootDirPath + "/cubey.obj";

        final List<String> srcFiles = Arrays.asList(cardMtlFile,
                glassMtlFile, recyledMtlFile, stoneMtlFile,
                woodMtlFile, cubeFile);

        final FileOutputStream fos = new FileOutputStream(
                Paths.get(cubeFile).getParent().toAbsolutePath() + "/building3d.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }

        zipOut.close();
        fos.close();
    }

    private void deleteTmpFiles(String pathDir) throws IOException {
        List<String> pathsToDelete = List.of(
                pathDir + "/card.mtl",
                pathDir + "/glass.mtl",
                pathDir + "/recy.mtl",
                pathDir + "/stone.mtl",
                pathDir + "/wood.mtl",
                pathDir + "/cubey.obj");

        for (String path : pathsToDelete) {
            Path fileToDeletePath = Paths.get(path);
            Files.delete(fileToDeletePath);
        }
        Path fileToDeletePath = Paths.get("src/test/resources/fileToDelete_jdk7.txt");
        Files.delete(fileToDeletePath);
    }

    private void saveMaterial(String fileName, String pathDir, String fileContents) throws FileNotFoundException {
        String targetFilePath = String.format("%s/%s.mtl", pathDir, fileName);
        PrintWriter pw = new PrintWriter(targetFilePath);

        pw.print(fileContents);

        pw.close();
    }

    public Building3DModelGenerator(Building building) {
        this.building = building;
    }

    public double[][] translated(double dx, double dy, double dz) {
        double[][] translated = new double[8][3];

        int r = 0;
        for (double[] vertex : translated) {
            vertex[0] = UNIT_CUBE[r][0] + dx;
            vertex[1] = UNIT_CUBE[r][1] + dy;
            vertex[2] = UNIT_CUBE[r][2] + dz;
            r++;
        }

        return translated;
    }

    private int[][] adjustedFaces(int dieNum) {
        int[][] adjusted = new int[6][4];
        int r = 0;
        for (int[] rows : adjusted) {
            rows[0] = UNIT_CUBE_FACES[r][0] + (8 * (dieNum - 1));
            rows[1] = UNIT_CUBE_FACES[r][1] + (8 * (dieNum - 1));
            rows[2] = UNIT_CUBE_FACES[r][2] + (8 * (dieNum - 1));
            rows[3] = UNIT_CUBE_FACES[r][3] + (8 * (dieNum - 1));
            r++;
        }
        return adjusted;
    }

    private String cardMtlFileContents() {
        String result = "";
        result += String.format("# Card Material%n");
        result += String.format("newmtl CardMaterial%n");
        result += String.format("Ka 0.0 0.0 1.0  # Ambient color (card)%n");
        result += String.format("Kd 0.0 0.0 1.0  # Diffuse color (card)%n");
        result += String.format("Ks 0.5 0.5 0.5  # Specular color (slight shine)%n");
        result += String.format("d 1.0           # Fully opaque%n");
        result += String.format("illum 2         # Illumination model. See https://paulbourke.net/dataformats/mtl/%n");
        return result;
    }

    private String glassMtlFileContents() {
        String result = "";
        result += String.format("# Glass Cube Material%n");
        result += String.format("newmtl GlassCubeMaterial%n");
        result += String.format("Ka 0.969 0.969 0.969  # Ambient color (glass)%n");
        result += String.format("Kd 0.969 0.969 0.969  # Diffuse color (glass)%n");
        result += String.format("Ks 0.5 0.5 0.5  # Specular color (slight shine)%n");
        result += String.format("d 1.0           # Fully opaque%n");
        result += String.format("illum 2         # Illumination model. See https://paulbourke.net/dataformats/mtl/%n");
        return result;
    }

    private String recyMtlFileContents() {
        String result = "";
        result += String.format("# Recycled Cube Material%n");
        result += String.format("newmtl RecycledCubeMaterial%n");
        result += String.format("Ka 0.094 0.592 0.329  # Ambient color (recycled)%n");
        result += String.format("Kd 0.094 0.592 0.329  # Diffuse color (recycled)%n");
        result += String.format("Ks 0.5 0.5 0.5  # Specular color (slight shine)%n");
        result += String.format("d 1.0           # Fully opaque%n");
        result += String.format("illum 2         # Illumination model. See https://paulbourke.net/dataformats/mtl/%n");
        return result;
    }

    private String stoneMtlFileContents() {
        String result = "";
        result += String.format("# Stone Cube Material%n");
        result += String.format("newmtl StoneCubeMaterial%n");
        result += String.format("Ka 0.3 0.3 0.3  # Ambient color (stone)%n");
        result += String.format("Kd 0.3 0.3 0.3  # Diffuse color (stone)%n");
        result += String.format("Ks 0.8 0.8 0.8  # Specular color (slight shine)%n");
        result += String.format("d 1.0           # Fully opaque%n");
        result += String.format("illum 2         # Illumination model. See https://paulbourke.net/dataformats/mtl/%n");
        return result;
    }

    private String woodMtlFileContents() {
        String result = "";
        result += String.format("# Wood Cube Material%n");
        result += String.format("newmtl WoodCubeMaterial%n");
        result += String.format("Ka 0.949 0.482 0.176  # Ambient color (wood)%n");
        result += String.format("Kd 0.949 0.482 0.176   # Diffuse color (wood)%n");
        result += String.format("Ks 0.8 0.8 0.8  # Specular color (slight shine)%n");
        result += String.format("d 1.0           # Fully opaque%n");
        result += String.format("illum 2         # Illumination model. See https://paulbourke.net/dataformats/mtl/%n");
        return result;
    }

    private String baseCard() {
        String result = "";
        result += String.format("# Material used for this card%n");
        result += String.format("usemtl CardMaterial%n");
        result += String.format("g card%n");
        result += String.format("%n");
        result += String.format("v 0.00 0.00 0.00%n");
        result += String.format("v 2.12 0.00 0.00%n");
        result += String.format("v 2.12 0.00 3.22%n");
        result += String.format("v 0.00 0.00 3.22%n");
        result += String.format("%n");
        result += String.format("f 1 2 3 4%n");
        return result;
    }

    private String oneCube(Die d, Space space, int level, int dieNumber) {
        String result = "";
        result += String.format("g cube-%02d%n", dieNumber);
        result += String.format("%n");
        result += materialUsed(d);
        result += String.format("%n");
        result += String.format("%s%n",
                vertices((space.colVal() - 1) * 1.07, (level - 1) * 1.07, (space.rowVal() - 1) * 1.07));
        result += String.format("%n");
        result += String.format("%s%n", faces(dieNumber));
        result += String.format("%n");

        return result;
    }

    private String libraries() {
        String result = "";
        result += String.format("# Material library references%n");
        result += String.format("mtllib card.mtl%n");
        result += String.format("mtllib stone.mtl%n");
        result += String.format("mtllib wood.mtl%n");
        result += String.format("mtllib recy.mtl%n");
        result += String.format("mtllib glass.mtl%n");

        return result;
    }

    private String facesAsText(int[] faces) {
        String result = "";
        for (int i : faces) {
            result += i + " ";
        }

        return result.trim();
    }

    private String faces(int dieNum) {
        String result = "";
        result += String.format("# Faces (using vertex indices)%n");

        int[][] faceList = adjustedFaces(dieNum);

        for (int[] faces : faceList) {
            result += String.format("f %s%n", facesAsText(faces));
        }

        return result;
    }

    private String vertices(double dx, double dy, double dz) {
        String result = "";
        double[][] translatedCube = translated(dx, dy, dz);
        for (double[] vertices : translatedCube) {
            result += String.format("v %s%n", vertexAsText(vertices));
        }
        return result;
    }

    private String vertexAsText(double... coord) {
        String x = String.format("%.2f", coord[0]);
        String y = String.format("%.2f", coord[1]);
        String z = String.format("%.2f", coord[2]);

        return x + " " + y + " " + z;
    }

    private String materialUsed(Die d) {
        String result = "";
        result += String.format("# Material used for this die%n");
        result += String.format("usemtl %s%n", materialDesc(d.getMaterial()));

        return result;
    }

    private String materialDesc(Material material) {
        return switch (material) {
            case GLASS -> "GlassCubeMaterial";
            case WOOD -> "WoodCubeMaterial";
            case STONE -> "StoneCubeMaterial";
            default -> "RecycledCubeMaterial";
        };
    }
}
