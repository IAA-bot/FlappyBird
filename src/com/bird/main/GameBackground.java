package com.bird.main;

import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.bird.util.Constant.*;

/**
 * @author IAA
 * @date 2025/9/2
 */
public class GameBackground {
    // 背景图片
    private BufferedImage background;

    // 构造方法，初始化背景图片
    public GameBackground() {
        background = GameUtil.loadBufferedImage(BACKGROUND_PATH);
    }

    // 绘制背景图片
    public void draw(Graphics g) {
        // 获取图片尺寸
        int width = background.getWidth();
//        int height = background.getHeight();
        // 所需图片数量
        int xCount = (GAME_WIDTH / width) + 1;
        for (int i = 0; i < xCount; i++) {
            g.drawImage(background, i * width, 0, null);
        }
    }
}
