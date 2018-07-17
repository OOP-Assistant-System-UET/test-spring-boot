package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolExample {


    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 10; i++) {
            Runnable runnable = new WorkerThread("" + i);
            executor.execute(runnable);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }

        System.out.println("All thread is finished");
    }
}
