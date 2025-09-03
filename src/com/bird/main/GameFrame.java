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
    // 是否开始
    private boolean isStart = false;
    private Image cover = Toolkit.getDefaultToolkit().getImage(START_IMG_PATH);
    // 最高分
    private int highScore = 0;
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
                if (!isStart && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    isStart = true;
                }
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
        if (!isStart) {
            // 绘制封面
            g.drawImage(cover, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("按Enter开始游戏", GAME_WIDTH / 2 - 150, GAME_HEIGHT - 100);
            return;
        }
        // 创建与窗口大小一致的缓冲区
        Image offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        Graphics gOff = offScreenImage.getGraphics();
        // 在缓冲区绘制背景
        gameBackground.draw(gOff);
        // 在缓冲区绘制鸟
        bird.draw(gOff);
        // 在缓冲区绘制所有障碍物
        barrierLayer.draw(gOff, bird);
        // 在缓冲区绘制分数
        gOff.setColor(Color.WHITE);
        gOff.setFont(new Font("微软雅黑", Font.BOLD, 30));
        gOff.drawString("分数：" + barrierLayer.getScore(), 30, 60);
        // 将缓冲区内容绘制到窗口
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        if (bird.isCanFly()) {
            paint(g); // 直接调用paint方法，双缓冲，避免闪烁
        } else {
            // 更新最高分
            int score = barrierLayer.getScore();
            if (score > highScore) {
                highScore = score;
            }
            // 绘制游戏结束图片
            Image gameOverImg = Toolkit.getDefaultToolkit().getImage(GAMEOVER_IMG_PATH);
            g.drawImage(gameOverImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
            // 显示分数和最高分
            g.setColor(Color.BLACK);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("本次得分：" + score, GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2);
            g.drawString("最高得分：" + highScore, GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2 + 50);
            g.setFont(new Font("微软雅黑", Font.PLAIN, 20));
            g.drawString("按Enter重新开始游戏", GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2 + 100);
            // 按Enter键重新开始游戏
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        initGame();
                    }
                }
            });
        }
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


