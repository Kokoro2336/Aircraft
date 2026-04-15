package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShootStrategy;

public class EliteEnemy extends AbstractAircraft {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new DirectShootStrategy(1, 15, 1, 4));
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
