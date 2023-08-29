package com.github.philsongzi.demos.javase.juc.printer.wait;

/**
 * 三线程交替打印ABC，使用synchronized+wait/notify。
 *
 * @author 小子松
 * @since 2023/8/29
 */
public class WaitNotifyABC {

    private int num = 0;
    private static final Object lock = new Object();
    public void print(int targetNum) {
        for (int i = 0; i < 100; i++) { // 当要求是轮流打印 n 次时，前面套上循环即可
            synchronized (lock) {
                while (num % 3 != targetNum) {  // 为何使用while而不是if？因为线程被唤醒后，需要再次判断条件是否满足
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                num++;
                System.out.print(Thread.currentThread().getName());
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        WaitNotifyABC waitNotifyABC = new WaitNotifyABC();
        new Thread(() -> {
            waitNotifyABC.print(0);
        },"A").start();
        new Thread(() -> {
            waitNotifyABC.print(1);
        },"B").start();
        new Thread(() -> {
            waitNotifyABC.print(2);
        },"C").start();
    }
}
