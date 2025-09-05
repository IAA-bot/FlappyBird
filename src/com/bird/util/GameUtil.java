package com.bird.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author IAA
 * @date 2025/9/2
 */
public class GameUtil {
    // 加载图片
    public static BufferedImage loadBufferedImage(String path) {
        try (InputStream inputStream = GameUtil.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("资源未找到" + path);
            }
            return ImageIO.read(inputStream); // 这里可以使用ImageIO.read(new File(path));来加载图片
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
