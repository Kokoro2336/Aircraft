package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.strategy.ScatterShootStrategy;

public class AceEnemy extends AbstractAircraft {

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new ScatterShootStrategy(3, 25, 1, 5));
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
