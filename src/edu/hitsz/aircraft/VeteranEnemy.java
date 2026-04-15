package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShootStrategy;

public class VeteranEnemy extends AbstractAircraft {

    public VeteranEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new DirectShootStrategy(2, 20, 1, 6));
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
