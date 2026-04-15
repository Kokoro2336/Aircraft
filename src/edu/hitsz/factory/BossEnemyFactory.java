package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        int speedX = Math.random() < 0.5 ? -2 : 2;
        return new BossEnemy(Main.WINDOW_WIDTH / 2, 80, speedX, 0, 600);
    }
}
