package com.example.thehillreloaded.gameplay.falling_objects;

public class PowerUp extends Junk {

    private static double tasso = 0.025;

    public PowerUp(int x, int y) {
        super(x,y);
    }

    public static double getTasso() {
        return tasso;
    }

    public static void setTasso(double newTasso) {
        tasso = newTasso;
    }

    public static void resetTasso() {
        tasso = 0.025;
    }

    public void applyPowerUp() {

    }
}
