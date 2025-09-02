package com.bird.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author IAA
 * @date 2025/9/2
 */
public class GameUtil {
    // 加载图片
    public static BufferedImage loadBufferedImage(String path) {
        try {
            return ImageIO.read(new FileInputStream(path)); // 这里可以使用ImageIO.read(new File(path));来加载图片
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
