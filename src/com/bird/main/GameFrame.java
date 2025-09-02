package com.bird.main;

// 静态导入常量
import static com.bird.util.Constant.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author IAA
 * @date 2025/9/2
 * 窗口类，实现游戏内容的绘制
 */
public class GameFrame extends Frame {
    // 实例化背景对象
    GameBackground gameBackground = new GameBackground();

    public GameFrame() {
        // 窗口可见性
        this.setVisible(true);
        // 设置窗口大小
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        // 设置窗口标题
        this.setTitle(GAME_TITLE);
        // 设置窗口位置
        this.setLocation(GAME_X, GAME_Y);
        // 固定窗口大小
        this.setResizable(false);
        // 关闭窗口
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 退出程序
            }
        });
        // 初始化游戏
        initGame();
        // 启动重绘线程
        new Run().start();
    }

    public void initGame() {
        gameBackground = new GameBackground();
    }

    class Run extends Thread {
        @Override
        public void run() {
            // 重绘窗口
            repaint();
            try {
                Thread.sleep(33); // 休眠40毫秒
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        // 创建与窗口大小一致的缓冲区
        Image offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        Graphics gOff = offScreenImage.getGraphics();
        // 在缓冲区绘制背景
        gameBackground.draw(gOff);
        // 将缓冲区内容绘制到窗口
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        paint(g); // 直接调用paint方法，双缓冲，避免闪烁
    }
}


