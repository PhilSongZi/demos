package com.github.philsongzi.demos.javase.juc.printer.wait;

/**
 * 两个线程交替打印奇偶数，使用synchronized+wait/notify。
 *
 * @author 小子松
 * @since 2023/8/29
 */
public class WaitNotifyOddEven {

    private Object monitor = new Object();
    private volatile int count;

    WaitNotifyOddEven(int initCount) {
        this.count = initCount;
    }

    private void printOddEven() {
        synchronized (monitor) {
            while (count < 10) {
                try {
                    System.out.print( Thread.currentThread().getName() + "：");
                    System.out.println(++count);
                    monitor.notifyAll();
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //防止count=10后，while()循环不再执行，有子线程被阻塞未被唤醒，导致主线程不能退出
            monitor.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WaitNotifyOddEven waitNotifyOddEven = new WaitNotifyOddEven(0);
        new Thread(waitNotifyOddEven::printOddEven, "odd").start();
        Thread.sleep(10); //为了保证线程odd先拿到锁
        new Thread(waitNotifyOddEven::printOddEven, "even").start();
    }
}
