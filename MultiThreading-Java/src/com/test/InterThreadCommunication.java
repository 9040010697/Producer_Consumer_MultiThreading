package com.test;
// Write a Program to print Odd and Even number interchangeably

import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class InterThreadCommunication {

    private static Object lockObject = new Object();

    private static final IntPredicate evenSelector = e -> e % 2 == 0;
    private static final IntPredicate oddSelector = e -> e % 2 != 0;
    private static final Integer[] range = { 1, 11 };

    public static void main(String[] args) throws Exception {
        CompletableFuture.runAsync(() -> InterThreadCommunication.runJob(oddSelector));
        CompletableFuture.runAsync(() -> InterThreadCommunication.runJob(evenSelector));
        Thread.sleep(1000);
    }

    private static void runJob(IntPredicate selector) {
        IntStream.range(range[0], range[1]).filter(selector).forEach(InterThreadCommunication::doThreadCommunication);
    }

    private static void doThreadCommunication(int i) {
        try {
            synchronized (lockObject) {
                System.out.println(i + " :: By " + Thread.currentThread().getName());
                lockObject.notify();
                lockObject.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

