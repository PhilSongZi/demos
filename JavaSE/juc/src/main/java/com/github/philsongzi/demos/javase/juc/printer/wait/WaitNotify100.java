package com.github.philsongzi.demos.javase.juc.printer.wait;

/**
 * n 个线程线程交替打印 1-100.
 *
 * @author 小子松
 * @since 2023/8/29
 */
public class WaitNotify100 {

    private int num;
    private static final Object LOCK = new Object();
    private int maxnum = 10;

    private void printABC(int targetNum) {
        while (true) {
            synchronized (LOCK) {
                while (num % 3 != targetNum) { // 为何使用while而不是if？因为线程被唤醒后，需要再次判断条件是否满足
                    if(num >= maxnum){
                        break;
                    }
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(num >= maxnum){
                    break;
                }
                num++;
                System.out.println(Thread.currentThread().getName() + ": " + num);
                LOCK.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        WaitNotify100  wait_notify_100 = new WaitNotify100();
        new Thread(() -> {
            wait_notify_100.printABC(0);
        }, "thread1").start();
        new Thread(() -> {
            wait_notify_100.printABC(1);
        }, "thread2").start();
        new Thread(() -> {
            wait_notify_100.printABC(2);
        }, "thread3").start();
    }
}
