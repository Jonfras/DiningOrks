package net.htlgkr.krejo.diningOrks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ork implements Callable<Integer> {
    int id;
    final Lock leftDagger;
    final Lock rightDagger;


    public Ork(int id, Lock ld, Lock rd) {
        this.id = id;
        leftDagger = ld;
        rightDagger = rd;
    }

    private void trackTime(String activity) {
        try {
            System.out.println("Ork " + id + " is " + activity + " at Time: " + System.nanoTime());
            Thread.sleep((long) (Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer call() throws Exception {
        while (true) {
            trackTime("thinking");

            if (leftDagger.tryLock()) {
                try {
                    trackTime("grabbed left fork");
                    if (rightDagger.tryLock()) {
                        try {
                            trackTime("grabbed right fork - EATING NOW");
                            trackTime("finished eating");
                        } finally {
                            trackTime("put down right fork");
                            rightDagger.unlock();
                        }
                    }
                } finally {
                    leftDagger.unlock();
                    trackTime("put down left fork");

                }
            }
        }
    }
}


