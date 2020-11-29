package com.zykspring.funnycore.threadPoolTest;

public class ThreeThread {

    private static int count = 0;
    private static final Object lock = new Object();
    private static Object object1 = new Object();
    private int i;

    public static void main(String[] args) {
        ThreeThread threeThread = new ThreeThread();
        new Thread(threeThread.new TurningRunner(), "偶数").start();
        // 确保偶数线程线先获取到锁
        try {
            Thread.sleep(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        new Thread(threeThread.new TurningRunner(), "奇数").start();
    }

    class TurningRunner implements Runnable {
        @Override
        public void run() {
            while (count <= 1000) {
                // 获取锁
                synchronized (lock) {
                    // 拿到锁就打印
                    System.out.println(Thread.currentThread().getName() + ": " + count++);
                    // 唤醒其他线程
                    lock.notifyAll();
                    try {
                        if (count <= 100) {
                            // 如果任务还没有结束，则让出当前的锁并休眠
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void jiaoti1(){
        Thread even = new Thread(() -> {
            while (count < 1000) {
                synchronized (lock) {
                    // 只处理偶数
                    if ((count & 1) == 0) {
                        System.out.println(Thread.currentThread().getName() + ": " + count++);
                    }
                }
            }
        }, "偶数");

        Thread odd = new Thread(() -> {
            while (count < 1000) {
                synchronized (lock) {
                    // 只处理奇数
                    if ((count & 1) == 1) {
                        System.out.println(Thread.currentThread().getName() + ": " + count++);
                    }
                }
            }
        }, "奇数");
        even.start();
        odd.start();
    }

    public void jiaoti2(){
        Thread threadA = new Thread(() -> {
            while(i<1000) {
                synchronized (object1) {
                    try {
//                    lock.lock();
//                    if((i&1) == 0){
                        System.out.println(Thread.currentThread().getName() + " run " + i);
                        i++;
                        object1.notifyAll();
                        object1.wait();
//                    }
                    }catch(Exception e){
                        e.printStackTrace();
                    }finally{
                        //                    lock.unlock();
                    }
                }
            }
        },"偶数");
        Thread threadB = new Thread(()->{
            while(i<1000){
                synchronized (object1) {
                    try {
                        //                    lock.lock();
                        //                    if((i&1) != 0){
                        System.out.println(Thread.currentThread().getName() + " run " + i);
                        i++;

                        object1.notifyAll();
                        object1.wait();
                        //                    }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
//                        lock.unlock();
                    }
                }
            }
        },"奇数");
        threadA.start();
        while(i == 0){}
        threadB.start();
    }
}