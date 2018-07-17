package com.example.demo;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for ( int i = 0; i < 10; i++) {
            Runnable runnable = new WorkerThread("" + i);
            executor.execute(runnable);
        }
        executor.shutdown();
        while (executor.isTerminated()==false) {
        }
        System.out.println("All thread is finishe");
    }
}
