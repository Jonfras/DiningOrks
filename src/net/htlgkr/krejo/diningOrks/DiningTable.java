package net.htlgkr.krejo.diningOrks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class DiningTable {
    public static void main(String[] args) {
        int size = 5;
        List<Ork> orks = new ArrayList<>();
        Dagger[] daggers = new Dagger[size];
        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < size; i++) {
            daggers[i] = new Dagger();
        }

        for (int i = 0; i < size; i++) {
            if (i!=0) {
                orks.add(new Ork(i, daggers[i-1], daggers[i], lock)) ;
            }
            else{
                orks.add(new Ork(i, daggers[size-1], daggers[i], lock));
            }
        }

        ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(size);
        List<Future<Integer>> futures = null;
        try {
            futures = executor.invokeAll(orks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
