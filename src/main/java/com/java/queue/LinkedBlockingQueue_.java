package com.java.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author lw by 14-7-22.
 */
public class LinkedBlockingQueue_ {


    private static void aVoid() {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>();
        //将指定元素插入到此队列的尾部（如果立即可行且不会超出此队列的容量），在成功时返回 true，如果此队列已满，则返回 false。
        boolean isOffer = linkedBlockingQueue.offer("aa");
        linkedBlockingQueue.offer("ee");

        try {
            // 等待1s
            isOffer = linkedBlockingQueue.offer("bb", 1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("offer(E e, long timeout, TimeUnit unit) run ... 在等待时被中断...");
        }

        //获取但不移除此队列的头；如果此队列为空，则返回 null。
        String head = linkedBlockingQueue.peek();

        //获取并移除此队列的头，如果此队列为空，则返回 null。
        head = linkedBlockingQueue.poll();

        try {
            //获取并移除此队列的头部，在指定的等待时间前等待可用的元素（如果有必要）。
            head = linkedBlockingQueue.poll(1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("poll(long timeout, TimeUnit unit) run ... 在等待时被中断...");
        }

        try {
            //TODO 获取并移除此队列的头部，在元素变得可用之前一直等待（如果有必要）。
            linkedBlockingQueue.take();
        } catch (InterruptedException e) {
            System.out.println("take()  run ... 在等待时被中断...");
        }
        try {
            linkedBlockingQueue.put("cc");
        } catch (InterruptedException e) {
            System.out.println("put(E e) run ... 在等待时被中断...");
        }

        //返回理想情况下（没有内存和资源约束）此队列可接受并且不会被阻塞的附加元素数量。
        int remainingCapacity2Size = linkedBlockingQueue.remainingCapacity();

        //移除元素，如果存在
        linkedBlockingQueue.remove("dd");

    }

    public static void main(String[] args) {
        aVoid();

    }
}
