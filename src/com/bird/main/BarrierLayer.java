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

    public BarrierLayer() {
        barriers = new ArrayList<>();
    }

    // 添加障碍物
    public void draw(Graphics g) {
//        Barrier barrier = new Barrier(300, 0, 300, Barrier.type.DOWN);
//        barriers.add(barrier);
//        Barrier barrier1 = new Barrier(400, 0, 300, Barrier.type.UP);
//        barriers.add(barrier1);
//        barriers.get(0).draw(g);
//        barriers.get(1).draw(g);
        logic();
        for (Barrier barrier : barriers) {
            barrier.draw(g);
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
        Barrier downBarrier = new Barrier(GAME_WIDTH, 0, downBarrierHeight, Barrier.type.DOWN);
        barriers.add(downBarrier);
        Barrier upBarrier = new Barrier(GAME_WIDTH, GAME_HEIGHT-upBarrierHeight, upBarrierHeight, Barrier.type.UP);
        barriers.add(upBarrier);
    }
}
