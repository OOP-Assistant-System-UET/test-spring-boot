package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for ( int i = 0; i < 10; i++) {
            Runnable runnable = new WorkerThread(""+ i);
            executor.execute(runnable);
            Thread.sleep(400);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {

        }
        System.out.println("All thread finished");
    }
}
