package com.bird.main;

import java.util.ArrayList;

/**
 * @author IAA
 * @date 2025/9/3
 */
public class BarrierPool {
    // 用于管理池中对象的容器
    private static ArrayList<Barrier> barrierPool = new ArrayList<>();
    // 初始对象数量
    private static final int INIT_COUNT = 16;
    // 池中最大对象数量
    private static final int MAX_COUNT = 20;

    static {
        // 初始化对象池
        for (int i = 0; i < INIT_COUNT; i++) {
            barrierPool.add(new Barrier());
        }
    }

    // 从对象池中获取一个对象
    public static Barrier getBarrier() {
        if (!barrierPool.isEmpty()) {
//            System.out.println("从对象池中获取一个对象");
            return barrierPool.removeLast();
        } else {
//            System.out.println("对象池为空，创建一个新对象");
            return new Barrier();
        }
    }
    // 将对象归还给对象池
    public static void returnBarrier(Barrier barrier) {
        // 重置对象状态
        barrier.setScored(false);
        if (barrierPool.size() < MAX_COUNT) {
//            System.out.println("将对象归还给对象池");
            barrierPool.add(barrier);
        } else {
//            System.out.println("对象池已满，无法归还对象");
        }
    }
}
