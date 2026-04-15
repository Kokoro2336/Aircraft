package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.*;
import edu.hitsz.prop.BaseProp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 游戏主面板，游戏启动
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    private final Timer timer;
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> props;

    private final int enemyMaxNumber = 6;

    private final double enemySpawnCycle = 20;
    private int enemySpawnCounter = 0;

    private final double shootCycle = 20;
    private int shootCounter = 0;

    private int score = 0;
    private boolean gameOverFlag = false;

    private final EnemyFactory[] normalEnemyFactories;
    private final EnemyFactory bossEnemyFactory;
    private final Random random = new Random();

    private final int bossScoreStep = 300;
    private int nextBossScore = bossScoreStep;

    public Game() {
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        normalEnemyFactories = new EnemyFactory[]{
                new MobEnemyFactory(),
                new EliteEnemyFactory(),
                new VeteranEnemyFactory(),
                new AceEnemyFactory()
        };
        bossEnemyFactory = new BossEnemyFactory();

        new HeroController(this, heroAircraft);
        this.timer = new Timer("game-action-timer", true);
    }

    public void action() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                spawnEnemyAction();
                shootAction();
                bulletsMoveAction();
                aircraftsMoveAction();
                propsMoveAction();
                crashCheckAction();
                postProcessAction();
                repaint();
                checkResultAction();
            }
        };
        timer.schedule(task, 0, timeInterval);
    }

    private void spawnEnemyAction() {
        enemySpawnCounter++;
        if (enemySpawnCounter < enemySpawnCycle || enemyAircrafts.size() >= enemyMaxNumber) {
            return;
        }
        enemySpawnCounter = 0;

        boolean hasBoss = false;
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (enemy instanceof BossEnemy && !enemy.notValid()) {
                hasBoss = true;
                break;
            }
        }

        if (!hasBoss && score >= nextBossScore) {
            enemyAircrafts.add(bossEnemyFactory.createEnemy());
            nextBossScore += bossScoreStep;
            return;
        }

        EnemyFactory factory = normalEnemyFactories[random.nextInt(normalEnemyFactories.length)];
        enemyAircrafts.add(factory.createEnemy());
    }

    private void shootAction() {
        shootCounter++;
        if (shootCounter < shootCycle) {
            return;
        }
        shootCounter = 0;

        heroBullets.addAll(heroAircraft.shoot());
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (BaseProp prop : props) {
            prop.forward();
        }
    }

    private void crashCheckAction() {
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (enemyAircraft instanceof MobEnemy) {
                            score += 10;
                        } else if (enemyAircraft instanceof EliteEnemy) {
                            score += 20;
                        } else if (enemyAircraft instanceof VeteranEnemy) {
                            score += 30;
                            if (Math.random() < 0.6) {
                                props.add(PropFactory.createRandom(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), false));
                            }
                        } else if (enemyAircraft instanceof AceEnemy) {
                            score += 40;
                            if (Math.random() < 0.8) {
                                props.add(PropFactory.createRandom(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), true));
                            }
                        } else if (enemyAircraft instanceof BossEnemy) {
                            score += 200;
                            for (int i = 0; i < 3; i++) {
                                props.add(PropFactory.createRandom(enemyAircraft.getLocationX(), enemyAircraft.getLocationY(), true));
                            }
                        }
                    }
                }
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        for (BaseProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop) || prop.crash(heroAircraft)) {
                prop.activate(heroAircraft);
                prop.vanish();
            }
        }
    }

    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    private void checkResultAction() {
        if (heroAircraft.getHp() <= 0) {
            timer.cancel();
            gameOverFlag = true;
            System.out.println("Game Over!");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2,
                null);

        paintScoreAndLife(g);
    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;

        g.setColor(new Color(0x3f51b5));
        g.setFont(new Font("Sans-serif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);

        if (this.gameOverFlag) {
            g.setColor(Color.RED);
            g.setFont(new Font("Sans-serif", Font.BOLD, 35));
            g.drawString("GAME OVER", Main.WINDOW_WIDTH / 2 - 95, Main.WINDOW_HEIGHT / 2);
        }
    }
}
