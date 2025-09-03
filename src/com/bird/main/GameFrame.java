package com.bird.main;

// 静态导入常量
import static com.bird.util.Constant.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author IAA
 * @date 2025/9/2
 * 窗口类，实现游戏内容的绘制
 */
public class GameFrame extends Frame {
    // 实例化背景对象
    private GameBackground gameBackground;
    // 实例化鸟对象
    private Bird bird;
    // 实例化障碍物层
    private BarrierLayer barrierLayer;

    public GameFrame() {
        // 初始化游戏，在窗口可见之前初始化，避免空指针异常(需要在paint方法中使用)
        initGame();
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
        // 启动重绘线程
        new Run().start();
        // 按键监听
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                add(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                minu(e);
            }
        });
    }

    public void initGame() {
        gameBackground = new GameBackground();
        bird = new Bird();
        barrierLayer = new BarrierLayer();
    }

    class Run extends Thread {
        @Override
        public void run() {
            while (true) {
                // 重绘窗口
                repaint();
                try {
                    Thread.sleep(40); // 休眠40毫秒
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
        // 在缓冲区绘制鸟
        bird.draw(gOff);
        // 在缓冲区绘制所有障碍物
        barrierLayer.draw(gOff);
        // 将缓冲区内容绘制到窗口
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        paint(g); // 直接调用paint方法，双缓冲，避免闪烁
    }

    // 按键按下事件
    public void add(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.setState(Bird.state.UP);
        }
    }
    // 按键释放事件
    public void minu(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.setState(Bird.state.DOWN);
        }
    }
}


