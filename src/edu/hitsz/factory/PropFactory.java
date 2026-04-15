package edu.hitsz.factory;

import edu.hitsz.prop.*;

import java.util.Random;

public class PropFactory {

    private static final Random RANDOM = new Random();

    public static BaseProp createProp(String type, int locationX, int locationY) {
        int speedY = 5;
        switch (type) {
            case "blood":
                return new BloodProp(locationX, locationY, 0, speedY);
            case "bullet":
                return new BulletProp(locationX, locationY, 0, speedY);
            case "bulletPlus":
                return new BulletPlusProp(locationX, locationY, 0, speedY);
            case "bomb":
                return new BombProp(locationX, locationY, 0, speedY);
            case "freeze":
                return new FreezeProp(locationX, locationY, 0, speedY);
            default:
                throw new IllegalArgumentException("Unknown prop type: " + type);
        }
    }

    public static BaseProp createRandom(int locationX, int locationY, boolean includeFreeze) {
        if (includeFreeze) {
            int index = RANDOM.nextInt(5);
            if (index == 0) {
                return createProp("blood", locationX, locationY);
            }
            if (index == 1) {
                return createProp("bullet", locationX, locationY);
            }
            if (index == 2) {
                return createProp("bulletPlus", locationX, locationY);
            }
            if (index == 3) {
                return createProp("bomb", locationX, locationY);
            }
            return createProp("freeze", locationX, locationY);
        }

        int index = RANDOM.nextInt(4);
        if (index == 0) {
            return createProp("blood", locationX, locationY);
        }
        if (index == 1) {
            return createProp("bullet", locationX, locationY);
        }
        if (index == 2) {
            return createProp("bulletPlus", locationX, locationY);
        }
        return createProp("bomb", locationX, locationY);
    }
}
