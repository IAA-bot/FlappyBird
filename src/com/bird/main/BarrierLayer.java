package com.bird.main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.bird.util.Constant.GAME_HEIGHT;
import static com.bird.util.Constant.GAME_WIDTH;

/**
 * @author IAA
 * @date 2025/9/3
 */
public class BarrierLayer {
    private List<Barrier> barriers;
    private Random random = new Random();
    private int upBarrierHeight;
    private int downBarrierHeight;
    private int score = 0;

    public BarrierLayer() {
        barriers = new ArrayList<>();
    }

    // 添加障碍物
    public void draw(Graphics g, Bird bird) {
        collide(bird);
        // 逻辑处理
        logic();
//        for (Barrier barrier : barriers) {
//            if (barrier.isVisible()) {
//                barrier.draw(g);
//            } else {
//                barriers.remove(barrier);
//                BarrierPool.returnBarrier(barrier);
//            }
//        }
        // 使用迭代器安全删除
        var iterator = barriers.iterator();
        while (iterator.hasNext()) {
            Barrier barrier = iterator.next();
            if (barrier.isVisible()) {
                barrier.draw(g);
                // 判断鸟是否刚通过管道
                if (barrier.getBarrierType() == Barrier.type.UP && barrier.isVisible() && !barrier.isScored()
                        && !barrier.getRect().contains(bird.getRect().x, bird.getRect().y)
                        && bird.getRect().x > barrier.getRect().x + barrier.getRect().width
                        && !barrier.getRect().contains(bird.getRect().x - Barrier.SPEED, bird.getRect().y)) {
                    score++;
                    barrier.setScored(true);
                }
            } else {
                iterator.remove();
                BarrierPool.returnBarrier(barrier);
            }
        }
    }

    // 障碍物随机生成
    public void logic() {
        if (barriers.isEmpty()) {
            createBarrier();
        } else {
            Barrier lastBarrier = barriers.get(barriers.size() - 1);
            if (lastBarrier.shouldRenew()) {
                createBarrier();
            }
        }
    }
    // 生成随机数
    public void randomize() {
        upBarrierHeight = random.nextInt(100, 500);
        downBarrierHeight = random.nextInt(100, 500);
        if (upBarrierHeight + downBarrierHeight > 500 || upBarrierHeight + downBarrierHeight < 400) {
            randomize();
        }
    }
    // 生成一组管道
    public void createBarrier() {
        randomize();
        insert(GAME_WIDTH, 0, downBarrierHeight, Barrier.type.DOWN);
        insert(GAME_WIDTH, GAME_HEIGHT-upBarrierHeight, upBarrierHeight, Barrier.type.UP);
    }

    // 从池中获取对象，封装成barrier存入barriers数组
    public void insert(int x, int y, int height, Barrier.type barrierType) {
        Barrier barrier = BarrierPool.getBarrier();
        barrier.setX(x);
        barrier.setY(y);
        barrier.setHeight(height);
        barrier.setBarrierType(barrierType);
        barrier.setVisible(true);
        barriers.add(barrier);
    }

    // 判断障碍物是否与鸟碰撞
    public boolean collide(Bird bird) {
        for (Barrier barrier : barriers) {
            if (barrier.isVisible() && bird.getRect().intersects(barrier.getRect())) {
                System.out.println("碰撞了");
                bird.setCanFly(false);
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        return score;
    }
}
