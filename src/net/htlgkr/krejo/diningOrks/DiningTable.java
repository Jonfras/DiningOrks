package net.htlgkr.krejo.diningOrks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningTable {
    public static void main(String[] args) {
        int size = 5;
        List<Ork> orks = new ArrayList<>();
        List<Lock> daggers = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            daggers.add(new ReentrantLock());
        }


        for (int i = 0; i < size; i++) {
            orks.add(new Ork(i, daggers.get(i), daggers.get((i + 1) % size)));
        }

        ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(size);
        List<Future<Integer>> futures = null;
        try {
            futures = executor.invokeAll(orks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
    }
}
