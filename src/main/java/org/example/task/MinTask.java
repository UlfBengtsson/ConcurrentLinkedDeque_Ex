package org.example.task;

public class MinTask implements Runnable {

    private int min = 0;

    @Override
    public void run() {
        System.out.print(min++ + "m ");
        if (min >= 60) {
            min = 0;
        }
    }
}