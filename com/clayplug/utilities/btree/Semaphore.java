package com.clayplug.utilities.btree;

public class Semaphore {

    private int permits;
    private int maxPermits;

    public Semaphore(int maxPermits) {
        this.maxPermits = maxPermits;
        this.permits = maxPermits;
    }

    public synchronized void acquire() {
        while (permits == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        if (permits > maxPermits) {
            permits = maxPermits;
        }
        notify();
    }

    public synchronized int getPermits() {
        return permits;
    }

    public synchronized int getMaxPermits() {
        return maxPermits;
    }

    public synchronized void setMaxPermits(int maxPermits) {
        this.maxPermits = maxPermits;
    }
}

