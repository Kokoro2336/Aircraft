package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import java.util.List;
import java.util.ArrayList;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;


public class BossEnemy extends AbstractAircraft {

    protected int bulletPower = 100; // Boss敌机的子弹威力更大
    protected int direction = 1;
    protected int shootNum = 5;

    BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // X方向上触碰到边界时反向
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            speedX = -speedX;
        }
        // Boss在Y方向上不移动
    }

    @Override
    // Boss机一次发射5颗子弹
    public List<BaseBullet> shoot() {
        List<BaseBullet> bullets = new ArrayList<>();
        // 此为中央子弹参数 
        int locationX = this.getLocationX();
        int locationY = this.getLocationY() + direction*2;
        int bulletSpeedX = 0;
        int bulletSpeedY = this.getSpeedY() + direction*20;

        for (int i = 0; i < shootNum; i++) {
            bullets.add(new EnemyBullet(locationX + (i - shootNum / 2), locationY + direction*2, bulletSpeedX, bulletSpeedY, this.bulletPower));
        }
        return bullets;
    }
}
