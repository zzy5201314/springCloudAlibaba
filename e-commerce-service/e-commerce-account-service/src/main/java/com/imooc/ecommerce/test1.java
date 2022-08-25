package com.imooc.ecommerce;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO: Description
 *
 * @author zzy
 * @date 2022/8/16
 */
public class test1 {

    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    private static List<Semaphore> list = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        for (int i = 1; i <= m; i++) {
            queue.offer(i);
        }

        for(int i=0;i<n;i++) {
            if(i == 0) {
                list.add(new Semaphore(1));
            } else {
                list.add(new Semaphore(0));
            }
            new Thread(new MyRunnable(i)).start();
        }
    }

    static class MyRunnable implements Runnable {

        private int threadIndex;

        public MyRunnable(int i) {
            threadIndex = i;
        }

        private Semaphore getNext() {
            if(threadIndex == list.size() -1) {
                return list.get(0);
            } else {
                return list.get(threadIndex + 1);
            }
        }

        @Override
        public void run() {
            while (queue.size() != 0) {
                try {
                    list.get(threadIndex).acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(queue.size() != 0) {
                    System.out.println(Thread.currentThread().getName() + " " + queue.poll());
                }
                this.getNext().release();
            }
        }
    }

}
