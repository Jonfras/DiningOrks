package net.htlgkr.krejo.diningOrks;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class Ork implements Callable<Integer> {
    private int id;
    private final Object leftDagger;
    private final Object rightDagger;
    static ReentrantLock lock;

    public Ork(int id, Object rightDagger, Object leftDagger, ReentrantLock lock) {
        this.id = id;
        this.leftDagger = leftDagger;
        this.rightDagger = rightDagger;
        Ork.lock = lock;
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
            trackTime("drinking");
            synchronized (leftDagger) {
                trackTime("LEFT DAGGER");
                lock.lock();
                try {
                    trackTime("RIGHT DAGGER - EATING");
                    Thread.sleep((long) (Math.random() * 100));
                    trackTime("finished Eating");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
//creates Deadlocks
//        while (true) {
//            trackTime("drinking");
//            synchronized (leftDagger) {
//                try {
//                    trackTime("LEFT DAGGER");
//                    synchronized (rightDagger) {
//                        try {
//                            trackTime("RIGHT DAGGER - EATING");
//                            Thread.sleep((long) (Math.random() * 100));
//                            trackTime("finished Eating");
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                } catch (RuntimeException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }

