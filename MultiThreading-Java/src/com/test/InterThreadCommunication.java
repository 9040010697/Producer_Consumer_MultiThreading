package com.test;
// Write a Program to print Odd and Even number interchangeably

import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class InterThreadCommunication {

    private static Object lockObject = new Object();

    private static IntPredicate evenSelector = e -> e % 2 == 0;
    private static IntPredicate oddSelector = e -> e % 2 != 0;
    private static Integer[] range = { 1, 11 };

    public static void main(String[] args) {
        CompletableFuture.runAsync(() -> InterThreadCommunication.runJob(oddSelector));
        CompletableFuture.runAsync(() -> InterThreadCommunication.runJob(evenSelector));
        Thread.currentThread().sleep(1000);
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
