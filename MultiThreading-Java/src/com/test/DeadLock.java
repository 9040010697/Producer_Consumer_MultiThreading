package com.test;

import java.util.concurrent.CompletableFuture;

public class DeadLock {

    private static Object lockObject = new Object();

    public static void main(String[] args) {
        CompletableFuture.runAsync(DeadLock::doThreadCommunication);
        CompletableFuture.runAsync(DeadLock::doThreadCommunication);
    }

    private static void doThreadCommunication() {
        try {
            synchronized (lockObject) {
                System.out.println("Run :: By " + Thread.currentThread().getName());
                lockObject.notify();
                System.out.println("Waiting for Notification " + Thread.currentThread().getName());
                lockObject.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
