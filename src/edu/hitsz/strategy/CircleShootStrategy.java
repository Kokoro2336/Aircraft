package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.ArrayList;
import java.util.List;

public class CircleShootStrategy implements ShootStrategy {

    private final int shootNum;
    private final int power;
    private final int speed;

    public CircleShootStrategy(int shootNum, int power, int speed) {
        this.shootNum = shootNum;
        this.power = power;
        this.speed = speed;
    }

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new ArrayList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();

        for (int i = 0; i < shootNum; i++) {
            double angle = 2 * Math.PI * i / shootNum;
            int speedX = (int) Math.round(speed * Math.cos(angle));
            int speedY = (int) Math.round(speed * Math.sin(angle));
            if (aircraft instanceof HeroAircraft) {
                bullets.add(new HeroBullet(x, y, speedX, speedY, power));
            } else {
                bullets.add(new EnemyBullet(x, y, speedX, speedY, power));
            }
        }
        return bullets;
    }
}
