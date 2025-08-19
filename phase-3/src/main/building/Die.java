package main.building;

import main.exceptions.runtime.InvalidDieFaceException;
import main.exceptions.runtime.InvalidDieMaterialException;
import main.space.Space;

/**
 * Represents a die with a specific material, face value, and optionally its
 * position (Space).
 */
public class Die {
    private final Material material;
    private final int face;
    private final Space space; // Optional: can be null

    public Die(String representation) {
        this(representation, null);
    }

    public Die(String representation, Space space) {
        if (representation == null || representation.length() != 2) {
            throw new IllegalArgumentException("Invalid die representation: " + representation);
        }

        char materialChar = representation.charAt(0);
        int faceValue;
        try {
            faceValue = Character.getNumericValue(representation.charAt(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid face value in: " + representation);
        }

        this.material = parseMaterial(materialChar);
        validateFace(faceValue);
        this.face = faceValue;
        this.space = space;
    }

    public Die(Material material, int face) {
        this(material, face, null);
    }

    public Die(Material material, int face, Space space) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }
        validateFace(face);
        this.material = material;
        this.face = face;
        this.space = space;
    }

    public Material getMaterial() {
        return material;
    }

    public int getFace() {
        return face;
    }

    public Space getSpace() {
        return space;
    }

    @Override
    public String toString() {
        String base = materialToChar(material) + face;
        if (space != null) {
            base += String.format(" @ (%d,%d)", space.rowVal(), space.colVal());
        }
        return base;
    }

    private Material parseMaterial(char materialChar) {
        switch (materialChar) {
            case 'W':
                return Material.WOOD;
            case 'R':
                return Material.RECYCLED;
            case 'S':
                return Material.STONE;
            case 'G':
                return Material.GLASS;
            default:
                throw new InvalidDieMaterialException("Invalid die material: " + materialChar + ".");
        }
    }

    private String materialToChar(Material material) {
        switch (material) {
            case WOOD:
                return "W";
            case RECYCLED:
                return "R";
            case STONE:
                return "S";
            case GLASS:
                return "G";
            default:
                throw new IllegalStateException("Unexpected material: " + material);
        }
    }

    private void validateFace(int face) {
        if (face < 1 || face > 6) {
            throw new InvalidDieFaceException("Invalid die face: " + face + ".");
        }
    }
}
