package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.strategy.CircleShootStrategy;

public class BossEnemy extends AbstractAircraft {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new CircleShootStrategy(20, 30, 6));
    }

    @Override
    public void forward() {
        locationX += speedX;
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            speedX = -speedX;
        }
    }
}
