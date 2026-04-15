package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.application.Main;

public class AceEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {
        int x = (int) (Math.random() * Main.WINDOW_WIDTH);
        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
        int speedX = Math.random() < 0.5 ? -3 : 3;
        return new AceEnemy(x, y, speedX, 8, 120);
    }
}
