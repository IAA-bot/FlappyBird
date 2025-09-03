package com.bird.main;

import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.bird.util.Constant.BIRD_IMG_PATH;

/**
 * @author IAA
 * @date 2025/9/3
 */
public class Bird {
    // 存放鸟的图片
    private final BufferedImage[] images;
    private static final int BIRD_IMG_COUNT = BIRD_IMG_PATH.length;
    // 定义鸟状态
    public enum state {UP, LEVEL, DOWN}
    // 当前状态，默认水平飞行
    private state currentState = state.LEVEL;
    // 鸟的移动方向(上下)
    private boolean up = false, down = false;
    // 鸟的位置
    private int x = 200, y = 322;

    // 控制鸟的飞行节奏
    private long lastFlyTime = 0; // 上次飞行时间
    private long flyTimer = 0; // 飞行计时器

    // 初始化
    public Bird() {
        images = new BufferedImage[BIRD_IMG_COUNT];
        for (int i = 0; i < BIRD_IMG_COUNT; i++) {
            images[i] = GameUtil.loadBufferedImage(BIRD_IMG_PATH[i]);
        }
    }
    // 鸟的移动方向
    public void flyLogic() {
        // 鸟的移动速度
        int speed = 5;
        if (up) {
            // 向上飞持续时间（毫秒）
            long flyDuration = 300;
            if (System.currentTimeMillis() - flyTimer < flyDuration) {
                y -= speed * 2;
                if (y < 20) y = 20;
            } else {
                up = false;
                down = true;
                currentState = state.DOWN;
            }
        } else if (down) {
            y += speed;
            // 控制鸟的下降高度
            if (y > 600) {
                y = 600;
                down = false;
            }
        }
    }

    // 绘制鸟
    public void draw(Graphics g) {
        int index = 1;
        switch (currentState) {
            case UP -> {index = 2; up = true; down = false;}
            case LEVEL -> {up = false; down = true;}
            case DOWN -> {index = 0; up = false; down = true;}
        }
        flyLogic();
        g.drawImage(images[index], x, y, null);
    }

    public void setState(state s) {
        long now = System.currentTimeMillis();
        // 冷却时间（毫秒）
        long cooldown = 450;
        // 如果当前状态是向上飞，并且冷却时间未到，则忽略此次状态改变
        if (s == state.UP && now - lastFlyTime >= cooldown) {
            flyTimer = now;
            up = true;
            down = false;
            currentState = state.UP;
            lastFlyTime = now;
        } else if (s == state.DOWN) {
            up = false;
            down = true;
            currentState = state.DOWN;
        }
    }
}
