package edu.hitsz.aircraft;

import java.util.List;
import java.util.ArrayList;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.application.Main;

public class EliteEnemy extends AbstractAircraft {

    protected int hp;
    protected int maxHp;
    protected int bulletPower = 10;

    protected int direction = 1; // 向下发射
    
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.hp = hp;
        this.maxHp = hp;
    }

    @Override
    public void forward() {
        // 精英敌机的移动方式与普通敌机相同
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> bullets = new ArrayList<>();
        int locationX = this.getLocationX();
        int locationY = this.getLocationY() + direction*2;
        int bulletSpeedX = 0;
        int bulletSpeedY = this.getSpeedY() + direction*2;

        bullets.add(new EnemyBullet(locationX, locationY + direction*2, bulletSpeedX, bulletSpeedY, this.bulletPower));
        return bullets;
    }
}
