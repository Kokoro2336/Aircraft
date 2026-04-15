package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.Main;

public class EliteEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        int x = (int) (Math.random() * Main.WINDOW_WIDTH);
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        return new EliteEnemy(x, y, 0, 8, 60);
    }
}
