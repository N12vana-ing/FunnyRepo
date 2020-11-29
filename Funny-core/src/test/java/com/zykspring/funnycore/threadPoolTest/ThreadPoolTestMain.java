package com.zykspring.funnycore.threadPoolTest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolTestMain {

    private static ThreadPoolExecutor threadPoolExecutor;

    private static int coreSize = 2;

    private static int maxSize = 20;

    private static long aliveTime = 10;

    private static BlockingQueue queue = new ArrayBlockingQueue(20);

    private static int i = 0;

    /**
     * lock
     * @param args
     */
    private static Object object1 = new Object();
    private static Lock lock = new ReentrantLock();
    private static Condition c1 = lock.newCondition();
    private static Condition c2 = lock.newCondition();


    public static void main(String[] args) {
        /*threadPoolExecutor = new ThreadPoolExecutor(coreSize,
                maxSize,
                aliveTime,
                TimeUnit.SECONDS,
                queue);

        System.out.println(threadPoolExecutor.toString());
        threadPoolExecutor.execute(()->{
            System.out.println(Thread.currentThread().getName()+" run !");
        });
        threadPoolExecutor.execute(()->{
            System.out.println(Thread.currentThread().getName()+" run !");
        });
        threadPoolExecutor.execute(()->{
            System.out.println(Thread.currentThread().getName()+" run !");
        });
        threadPoolExecutor.execute(()->{
            System.out.println(Thread.currentThread().getName()+" run !");
        });
        threadPoolExecutor.execute(()->{
            System.out.println(Thread.currentThread().getName()+" run !");
        });
        try {
            Thread.currentThread().join(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(threadPoolExecutor.toString());
        threadPoolExecutor.shutdown();//不加这个程序会一直运行*/

        ThreadPoolTestMain threadPoolTestMain = new ThreadPoolTestMain();
        Thread threadA = new Thread(threadPoolTestMain.new testRunnable1(),"偶数");
        Thread threadB = new Thread(threadPoolTestMain.new testRunnable2(),"奇数");
        threadA.start();
        threadB.start();

//        try {
//            threadA.join();
//            threadB.join();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    class testRunnable1 implements Runnable{
        @Override
        public void run() {
            while(i<1000){
                try {
                    lock.lock();
//                    if((i&1)==0){
                        System.out.println(Thread.currentThread().getName()+" run "+i);
                        i++;
                        c2.await();
                        c2.signal();
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    class testRunnable2 implements Runnable{
        @Override
        public void run() {
            while(i<1000){
                try {
                    lock.lock();
//                    if((i&1) != 0){
                        System.out.println(Thread.currentThread().getName()+" run "+i);
                        i++;
                        c2.signal();
                        c2.await();
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

}
