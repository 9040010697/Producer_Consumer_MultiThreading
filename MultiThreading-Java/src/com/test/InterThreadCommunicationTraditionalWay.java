package com.test;
// Write a Program to print Odd and Even number interchangeably

/**
1 :: By Odd Thread
2 :: By Even Thread
3 :: By Odd Thread
4 :: By Even Thread
5 :: By Odd Thread
6 :: By Even Thread
7 :: By Odd Thread
8 :: By Even Thread
9 :: By Odd Thread
10 :: By Even Thread

*/

import java.util.Arrays;
import java.util.List;

public class InterThreadCommunicationTraditionalWay {
    private static List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private static Object lockObject = new Object();

    public static void main(String[] args) {
        Thread t1 = new OddThread();
        t1.setName("Odd Thread");
        Thread t2 = new EvenThread();
        t2.setName("Even Thread");
        t1.start();
        t2.start();
    }

    static class OddThread extends Thread {
        @Override
        public void run() {
            try {
                synchronized (lockObject) {
                    for (Integer i : list) {
                        if (i % 2 != 0) {
                            System.out.println(i + " :: By " + Thread.currentThread().getName());
                            lockObject.notify();
                            lockObject.wait();
                        }
                    }
                }
            } catch (

            Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    static class EvenThread extends Thread {
        @Override
        public void run() {
            try {
                synchronized (lockObject) {
                    for (Integer i : list) {
                        if (i % 2 == 0) {
                            System.out.println(i + " :: By " + Thread.currentThread().getName());
                            lockObject.notify();
                            lockObject.wait(50);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
