package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.ArrayList;
import java.util.List;

public class DirectShootStrategy implements ShootStrategy {

    private final int shootNum;
    private final int power;
    private final int direction;
    private final int bulletSpeedY;

    public DirectShootStrategy(int shootNum, int power, int direction, int bulletSpeedY) {
        this.shootNum = shootNum;
        this.power = power;
        this.direction = direction;
        this.bulletSpeedY = bulletSpeedY;
    }

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new ArrayList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction * 2;
        int speedY = aircraft.getSpeedY() + direction * bulletSpeedY;

        for (int i = 0; i < shootNum; i++) {
            int speedX = 0;
            int locationX = x + (i * 2 - shootNum + 1) * 10;
            if (aircraft instanceof HeroAircraft) {
                bullets.add(new HeroBullet(locationX, y, speedX, speedY, power));
            } else {
                bullets.add(new EnemyBullet(locationX, y, speedX, speedY, power));
            }
        }
        return bullets;
    }
}
