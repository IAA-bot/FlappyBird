package com.bird.main;

import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.bird.util.Constant.*;

/**
 * @author IAA
 * @date 2025/9/3
 */
public class Barrier {
    // 存放障碍物的图片
    private static final BufferedImage[] images;

    static {
        final int COUNT = PIPE_IMG_PATH.length;
        images = new BufferedImage[COUNT];
        for (int i = 0; i < COUNT; i++) {
            images[i] = GameUtil.loadBufferedImage(PIPE_IMG_PATH[i]);
        }
    }

    // 障碍物的位置
    private int x, y;
    // 障碍物的高度
    private int height;
    // 障碍物类型
    public enum type {UP, DOWN}
    private type barrierType;
    // 障碍物高度和宽度
    public static final int UP_PIPE_HEIGHT = images[0].getHeight();
    public static final int DOWN_PIPE_HEIGHT = images[1].getHeight();
    public static final int MID_UP_PIPE_HEIGHT = images[2].getHeight();
    public static final int MID_DOWN_PIPE_HEIGHT = images[3].getHeight();
    public static final int PIPE_WIDTH = images[0].getWidth();
    // 障碍物移动速度
    public static final int SPEED = 3;
    // 障碍物状态
    public boolean visible;
    // 障碍物矩形判定框
    private Rectangle rect;
    // 是否计分了
    private boolean scored = false;

    public Rectangle getRect() {
        return rect;
    }
    // 构造方法
    public Barrier() {
        rect = new Rectangle();
    }

    public Barrier(int x, int y,int height, type barrierType) {
        this.x = x;
        this.y = y;
        this.barrierType = barrierType;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public type getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(type barrierType) {
        this.barrierType = barrierType;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // 绘制障碍物
    public void draw(Graphics g) {
        switch (barrierType) {
            case DOWN -> drawDownBarrier(g);
            case UP -> drawUpBarrier(g);
        }
    }
    // 绘制朝下的障碍物
    private void drawDownBarrier(Graphics g) {
        // 求出所需中部图片的块数
        int count = (height - DOWN_PIPE_HEIGHT) / MID_DOWN_PIPE_HEIGHT + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(images[3], x, y+i*MID_DOWN_PIPE_HEIGHT, null);
        }
        // 绘制顶部图片
        g.drawImage(images[1], x, y+count*MID_UP_PIPE_HEIGHT, null);
        drawRect(g);
        // 障碍物移动
        x -= SPEED;
        if (x < -78) {
            visible = false;
        }
    }
    // 绘制朝上的障碍物
    private void drawUpBarrier(Graphics g) {
        // 求出所需中部图片的块数
        int count = (height - UP_PIPE_HEIGHT) / MID_UP_PIPE_HEIGHT + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(images[2], x, y+UP_PIPE_HEIGHT+i*MID_UP_PIPE_HEIGHT, null);
        }
        // 绘制顶部图片
        g.drawImage(images[0], x, y, null);
        drawRect(g);
        // 障碍物移动
        x -= SPEED;
        if (x < -PIPE_WIDTH) {
            visible = false;
        }
    }
    // 判断是否需要再生成障碍物
    public boolean shouldRenew() {
        return GAME_WIDTH - x > 200;
    }
    // 绘制障碍物的矩形判定框
    public void drawRect(Graphics g) {
        rect.setBounds(x, y, PIPE_WIDTH, height);
//        g.drawRect(rect.x, rect.y, rect.width, rect.height);
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }
}
