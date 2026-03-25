package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import java.util.List;
import java.util.ArrayList;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

public class VeteranEnemy extends EliteEnemy {

    protected int bulletPower = 20; // 老兵敌机的子弹威力更大
    protected int direction = 1;

    public VeteranEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }
    
    @Override
    public void forward() {
        super.forward();
        // X方向上触碰到边界时反向
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            speedX = -speedX;
        }
        // Y方向上触碰到边界时消失
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
        int bulletSpeedY = this.getSpeedY() + direction*5;

        bullets.add(new EnemyBullet(locationX, locationY + direction*2, bulletSpeedX, bulletSpeedY, this.bulletPower));
        return bullets;
    }
}
