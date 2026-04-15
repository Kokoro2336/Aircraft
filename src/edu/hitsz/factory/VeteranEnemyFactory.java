package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.VeteranEnemy;
import edu.hitsz.application.Main;

public class VeteranEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        int x = (int) (Math.random() * Main.WINDOW_WIDTH);
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedX = Math.random() < 0.5 ? -2 : 2;
        return new VeteranEnemy(x, y, speedX, 8, 90);
    }
}
