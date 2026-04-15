package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.CircleShootStrategy;

public class BulletPlusProp extends BaseProp {

    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        heroAircraft.setShootStrategy(new CircleShootStrategy(12, 30, 6));
        System.out.println("FirePlusSupply active");
    }
}
