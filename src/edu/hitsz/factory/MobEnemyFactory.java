package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        int x = (int) (Math.random() * Main.WINDOW_WIDTH);
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        return new MobEnemy(x, y, 0, 10, 30);
    }
}
