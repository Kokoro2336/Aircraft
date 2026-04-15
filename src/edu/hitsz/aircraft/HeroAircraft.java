package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShootStrategy;

/**
 * 英雄飞机，游戏玩家操控（单例）
 */
public class HeroAircraft extends AbstractAircraft {

    private static final HeroAircraft HERO_AIRCRAFT = new HeroAircraft();

    private HeroAircraft() {
        super(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0,
                0,
                100
        );
        this.setShootStrategy(new DirectShootStrategy(1, 30, -1, 5));
    }

    public static HeroAircraft getInstance() {
        return HERO_AIRCRAFT;
    }

    @Override
    public void forward() {
    }
}
